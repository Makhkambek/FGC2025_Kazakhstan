package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.SubSystems.Wheel;

public class SpinWheelCommand {
    private Wheel wheel;

    public SpinWheelCommand(Wheel wheel) {
        this.wheel = wheel;
    }

    public void initialize() {
        wheel.start();
    }

    public boolean isFinished() {
        return true;
    }

    public void end() {
        wheel.stop();
    }
}