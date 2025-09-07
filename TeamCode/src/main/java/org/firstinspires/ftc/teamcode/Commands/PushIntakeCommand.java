package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class PushIntakeCommand {
    private Intake intake;
    private boolean isFinished = false;
    private ElapsedTime delayTimer = new ElapsedTime();
    private static final int PUSH_POSITION = 60;
    private int state = 0;

    public PushIntakeCommand(Intake intake) {
        this.intake = intake;
    }

    public void initialize() {
        intake.setTargetLeft(PUSH_POSITION);
        intake.setTargetRight(intake.getRightPosition());
        state = 0;
        isFinished = false;
        delayTimer.reset();
    }

    public void execute() {
        intake.update();

        if (state == 0) {
            if (Math.abs(intake.getLeftPosition() - PUSH_POSITION) < 10) {
                delayTimer.reset();
                state = 1;
            }
        } else if (state == 1) {
            if (delayTimer.seconds() >= 1.0) {
                intake.setTargetRight(PUSH_POSITION);
                state = 2;
            }
        } else if (state == 2) {
            if (Math.abs(intake.getRightPosition() - PUSH_POSITION) < 10) {
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