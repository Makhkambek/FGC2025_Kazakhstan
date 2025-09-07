package org.firstinspires.ftc.teamcode.Controllers;

import org.firstinspires.ftc.teamcode.Commands.SpinWheelCommand;
import org.firstinspires.ftc.teamcode.SubSystems.Wheel;

public class WheelController {
    private Wheel wheel;
    private SpinWheelCommand spinCommand;
    private boolean isSpinning = false;

    public WheelController(Wheel wheel) {
        this.wheel = wheel;
        this.spinCommand = new SpinWheelCommand(wheel);
    }

    public void update(boolean leftBumperPressed) {
        if (leftBumperPressed && !isSpinning) {
            spinCommand.initialize();
            isSpinning = true;
        } else if (!leftBumperPressed && isSpinning) {
            spinCommand.end();
            isSpinning = false;
        }
    }
}