package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Controllers.HangController;
import org.firstinspires.ftc.teamcode.Controllers.IntakeController;
import org.firstinspires.ftc.teamcode.Controllers.WheelController;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain;
import org.firstinspires.ftc.teamcode.SubSystems.Hang;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.SubSystems.Wheel;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        Hang hang = new Hang(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Wheel wheel = new Wheel(hardwareMap);
        DriveTrain driveTrain = new DriveTrain(hardwareMap);

        GamepadEx gamepad1Ex = new GamepadEx(gamepad1);

        HangController hangController = new HangController(hang, gamepad1Ex);
        IntakeController intakeController = new IntakeController(intake);
        WheelController wheelController = new WheelController(wheel);

        boolean wasRightTriggerPressed = false;

        waitForStart();

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();

            hangController.update();
            intakeController.update(
                    gamepad1Ex.getButton(GamepadKeys.Button.RIGHT_BUMPER),
                    gamepad1Ex.getButton(GamepadKeys.Button.Y)
            );
            wheelController.update(gamepad1Ex.getButton(GamepadKeys.Button.LEFT_BUMPER));

            boolean isDpadUpPressed = gamepad1Ex.getButton(GamepadKeys.Button.DPAD_UP);
            if (isDpadUpPressed && !wasRightTriggerPressed) {
                driveTrain.setSlowMode(!driveTrain.isSlowMode());
            }
            wasRightTriggerPressed = isDpadUpPressed;

            double drive = -gamepad1Ex.getLeftY();
            double turn = gamepad1Ex.getRightX();
            driveTrain.tankDrive(drive, turn);

            telemetry.addData("Status", "Running");
            telemetry.addData("Left Intake Position", intake.getLeftPosition());
            telemetry.addData("Right Intake Position", intake.getRightPosition());
            telemetry.update();
        }

        CommandScheduler.getInstance().reset();
    }
}