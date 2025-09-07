package org.firstinspires.ftc.teamcode.SubSystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hang extends SubsystemBase {
    private DcMotorEx leftMotor;
    private DcMotorEx rightMotor;

    public Hang(HardwareMap hardwareMap) {
        super();
        leftMotor = hardwareMap.get(DcMotorEx.class, "leftHang");
        rightMotor = hardwareMap.get(DcMotorEx.class, "rightHang");

        leftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        leftMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        stop();
    }

    public void setPower(double power) {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }

    public void stop() {
        setPower(0);
    }
}