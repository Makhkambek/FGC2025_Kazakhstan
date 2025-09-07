package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Wheel {
    private DcMotorEx wheelMotor;

    public Wheel(HardwareMap hardwareMap) {
        wheelMotor = hardwareMap.get(DcMotorEx.class, "wheel_motor");
        wheelMotor.setDirection(DcMotorEx.Direction.FORWARD);
        wheelMotor.setPower(0);
    }

    public void start() {
        wheelMotor.setPower(1.0);
    }

    public void stop() {
        wheelMotor.setPower(0);
    }
}