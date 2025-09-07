package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.SubSystems.Hang;

public class HangForwardCommand extends CommandBase {
    private final Hang hang;
    private final double power;

    public HangForwardCommand(Hang hang, double power) {
        this.hang = hang;
        this.power = power;

        addRequirements(hang);
    }

    @Override
    public void initialize() {
        hang.setPower(power);
    }

    @Override
    public void end(boolean interrupted) {
        hang.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}