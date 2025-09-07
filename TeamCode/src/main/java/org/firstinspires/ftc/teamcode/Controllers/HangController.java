package org.firstinspires.ftc.teamcode.Controllers;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.SubSystems.Hang;
import org.firstinspires.ftc.teamcode.Commands.HangForwardCommand;
import org.firstinspires.ftc.teamcode.Commands.HangReverseCommand;

public class HangController {
    private final Hang hang;
    private final GamepadEx gamepad;
    private boolean wasRightTriggerPressed = false;
    private boolean wasLeftTriggerPressed = false;

    public HangController(Hang hang, GamepadEx gamepad) {
        this.hang = hang;
        this.gamepad = gamepad;
    }

    public void update() {
        float rightTrigger = (float) gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);
        float leftTrigger = (float) gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);

        boolean isRightTriggerPressed = rightTrigger > 0.1;
        boolean isLeftTriggerPressed = leftTrigger > 0.1;

        if (isLeftTriggerPressed && !wasLeftTriggerPressed) {
//            CommandScheduler.getInstance().cancelAll();
            CommandScheduler.getInstance().schedule(new HangForwardCommand(hang, 1.0));
        } else if (isRightTriggerPressed && !wasRightTriggerPressed) {
//            CommandScheduler.getInstance().cancelAll();
            CommandScheduler.getInstance().schedule(new HangReverseCommand(hang, 1.0));
        } else if (!isRightTriggerPressed && !isLeftTriggerPressed && (wasRightTriggerPressed || wasLeftTriggerPressed)) {
            CommandScheduler.getInstance().cancelAll();
            hang.stop();
        }

        wasRightTriggerPressed = isRightTriggerPressed;
        wasLeftTriggerPressed = isLeftTriggerPressed;
    }
}