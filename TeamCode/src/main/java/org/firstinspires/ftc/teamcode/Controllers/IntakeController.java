package org.firstinspires.ftc.teamcode.Controllers;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Commands.CloseIntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.OpenIntakeCommand;
import org.firstinspires.ftc.teamcode.Commands.PushIntakeCommand;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;

public class IntakeController {
    private Intake intake;
    private OpenIntakeCommand openCommand;
    private CloseIntakeCommand closeCommand;
    private PushIntakeCommand pushCommand;
    private boolean rightBumperPressed = false;
    private boolean aButtonPressed = false;
    private int rightBumperToggle = 0;
    private ElapsedTime timer = new ElapsedTime();
    private boolean isInitialPositionActive = false;

    public IntakeController(Intake intake) {
        this.intake = intake;
        this.openCommand = new OpenIntakeCommand(intake);
        this.closeCommand = new CloseIntakeCommand(intake);
        this.pushCommand = new PushIntakeCommand(intake);
    }

    public void update(boolean rightBumper, boolean aButton) {
        if (rightBumper && !rightBumperPressed) {
            rightBumperPressed = true;
            rightBumperToggle = (rightBumperToggle + 1) % 2;

            if (rightBumperToggle == 0) {
                timer.reset();
                openCommand.initialize();
                isInitialPositionActive = false;
            } else if (rightBumperToggle == 1) {
                timer.reset();
                closeCommand.initialize();
                isInitialPositionActive = false;
            }
        }
        if (!rightBumper) {
            rightBumperPressed = false;
        }

        if (aButton && !aButtonPressed) {
            aButtonPressed = true;
            timer.reset();
            pushCommand.initialize();
            isInitialPositionActive = false;
        }
        if (!aButton) {
            aButtonPressed = false;
        }

        if (rightBumperToggle == 0 && !openCommand.isFinished()) {
            openCommand.execute();
        } else if (rightBumperToggle == 1 && !closeCommand.isFinished()) {
            closeCommand.execute();
        } else if (!pushCommand.isFinished()) {
            pushCommand.execute();
        }

        if (openCommand.isFinished()) {
            openCommand.end();
        }
        if (closeCommand.isFinished()) {
            closeCommand.end();
        }
        if (pushCommand.isFinished()) {
            pushCommand.end();
        }
    }
}