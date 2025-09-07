package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Intake {
    private DcMotorEx leftMotor;
    private DcMotorEx rightMotor;

    public static final int CLOSED = 0;
    public static final int OPEN = 120;

    private int targetLeft = CLOSED;
    private int targetRight = CLOSED;

    private double integralSumLeft = 0;
    private double lastErrorLeft = 0;
    private double integralSumRight = 0;
    private double lastErrorRight = 0;

    private ElapsedTime timer = new ElapsedTime();
    private static final double INTEGRAL_LIMIT = 50.0;
    public static final double kP = 0.03;
    public static final double kI = 0.000;
    public static final double kD = 0.000;
    public static final double kF = 0.00;

    public Intake(HardwareMap hardwareMap) {
        leftMotor = hardwareMap.get(DcMotorEx.class, "intake_left");
        rightMotor = hardwareMap.get(DcMotorEx.class, "intake_right");

        leftMotor.setDirection(DcMotorEx.Direction.FORWARD);
        rightMotor.setDirection(DcMotorEx.Direction.REVERSE);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        resetEncoders();
    }

    private void resetEncoders() {
        leftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        targetLeft = CLOSED;
        targetRight = CLOSED;
        integralSumLeft = 0;
        integralSumRight = 0;
        lastErrorLeft = 0;
        lastErrorRight = 0;
        timer.reset();
    }

    public void setPower(double power) {
        leftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        leftMotor.setPower(clampPower(power));
        rightMotor.setPower(clampPower(power));
    }

    public void stop() {
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        resetEncoders();
    }

    public void setTargetLeft(int target) {
        targetLeft = target;
        integralSumLeft = 0;
    }

    public void setTargetRight(int target) {
        targetRight = target;
        integralSumRight = 0;
    }

    public void update() {
        double deltaTime = timer.seconds();
        if (deltaTime < 0.001) deltaTime = 0.001;

        // PID for left motor
        double positionLeft = leftMotor.getCurrentPosition();
        double errorLeft = targetLeft - positionLeft;
        integralSumLeft = clampIntegral(integralSumLeft + errorLeft * deltaTime);
        double derivativeLeft = (errorLeft - lastErrorLeft) / deltaTime;
        double outputLeft = (kP * errorLeft) + (kI * integralSumLeft) + (kD * derivativeLeft) + kF;
        leftMotor.setPower(clampPower(outputLeft));

        // PID for right motor
        double positionRight = rightMotor.getCurrentPosition();
        double errorRight = targetRight - positionRight;
        integralSumRight = clampIntegral(integralSumRight + errorRight * deltaTime);
        double derivativeRight = (errorRight - lastErrorRight) / deltaTime;
        double outputRight = (kP * errorRight) + (kI * integralSumRight) + (kD * derivativeRight) + kF;
        rightMotor.setPower(clampPower(outputRight));

        lastErrorLeft = errorLeft;
        lastErrorRight = errorRight;
        timer.reset();
    }

    private double clampPower(double power) {
        return Math.max(-1.0, Math.min(1.0, power));
    }

    private double clampIntegral(double integral) {
        return Math.max(-INTEGRAL_LIMIT, Math.min(INTEGRAL_LIMIT, integral));
    }

    public int getLeftPosition() {
        return leftMotor.getCurrentPosition();
    }

    public int getRightPosition() {
        return rightMotor.getCurrentPosition();
    }
}