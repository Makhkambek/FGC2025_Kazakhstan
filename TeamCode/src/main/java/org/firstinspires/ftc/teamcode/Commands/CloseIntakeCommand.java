package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class CloseIntakeCommand {
    private Intake intake;
    private boolean isFinished = false;
    private ElapsedTime delayTimer = new ElapsedTime();
    private int state = 0;

    public CloseIntakeCommand(Intake intake) {
        this.intake = intake;
    }

    public void initialize() {
        intake.setTargetRight(Intake.CLOSED);
        intake.setTargetLeft(intake.getLeftPosition());
        state = 0;
        isFinished = false;
        delayTimer.reset();
    }

    public void execute() {
        intake.update();

        if (state == 0) {
            if (Math.abs(intake.getRightPosition() - Intake.CLOSED) < 10) {
                delayTimer.reset();
                state = 1;
            }
        } else if (state == 1) {
            if (delayTimer.seconds() >= 0.5) {
                intake.setTargetLeft(Intake.CLOSED);
                state = 2;
            }
        } else if (state == 2) {
            if (Math.abs(intake.getLeftPosition() - Intake.CLOSED) < 10) {
                isFinished = true;
            }
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void end() {
        intake.setPower(0);
    }
}