package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class OpenIntakeCommand {
    private Intake intake;
    private boolean isFinished = false;
    private ElapsedTime delayTimer = new ElapsedTime();
    private int state = 0;

    public OpenIntakeCommand(Intake intake) {
        this.intake = intake;
    }

    public void initialize() {
//        intake.setTargetLeft(Intake.OPEN);
//        intake.setTargetRight(Intake.CLOSED);
        intake.setTargetLeft(Intake.OPEN);
        intake.setTargetRight(Intake.CLOSED);
        state = 0;
        isFinished = false;
        delayTimer.reset();
    }

    public void execute() {
        intake.update();

        if (state == 0) {
            if (Math.abs(intake.getLeftPosition() - Intake.OPEN) < 10) {
                delayTimer.reset();
                state = 1;
            }
        } else if (state == 1) {
            if (delayTimer.seconds() >= 0.8) {
                intake.setTargetRight(Intake.OPEN);
                state = 2;
            }
        } else if (state == 2) {
            if (Math.abs(intake.getRightPosition() - Intake.OPEN) < 10) {
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