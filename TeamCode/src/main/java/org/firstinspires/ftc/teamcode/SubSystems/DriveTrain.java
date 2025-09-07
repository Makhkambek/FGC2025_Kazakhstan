package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveTrain {
    private DcMotorEx leftBack;
    private DcMotorEx rightBack;
    private boolean slowMode = false;

    public DriveTrain(HardwareMap hardwareMap) {
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");

        rightBack.setDirection(DcMotorEx.Direction.FORWARD);
        leftBack.setDirection(DcMotorEx.Direction.REVERSE);

        leftBack.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        stop();
    }

    public void tankDrive(double drive, double turn) {
        double leftPower = drive - turn;
        double rightPower = drive + turn;

        leftPower = Math.max(-1.0, Math.min(1.0, leftPower));
        rightPower = Math.max(-1.0, Math.min(1.0, rightPower));

        if (slowMode) {
            leftPower *= 0.4;
            rightPower *= 0.4;
        }

        leftBack.setPower(leftPower);
        rightBack.setPower(rightPower);
    }

    public void setSlowMode(boolean slow) {
        slowMode = slow;
    }

    public boolean isSlowMode() {
        return slowMode;
    }

    public void stop() {
        leftBack.setPower(0);
        rightBack.setPower(0);
    }
}