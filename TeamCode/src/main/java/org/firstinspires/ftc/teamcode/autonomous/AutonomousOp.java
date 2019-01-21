package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.GoldDetectorManager;
import org.firstinspires.ftc.teamcode.motors.WheelMotors;

public abstract class AutonomousOp extends LinearOpMode {

    private static final double DRIVE_POWER = 0.2;
    protected WheelMotors wheelMotors = null;
    protected final GoldDetectorManager detectorManager = new GoldDetectorManager();

    @Override
    public void runOpMode() throws InterruptedException {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
    }

    private void waitForMotors() throws InterruptedException {
        // Wait for the Motor to finish
        while (opModeIsActive()
                && (wheelMotors.TL.isBusy()
                || wheelMotors.TR.isBusy()
                || wheelMotors.BL.isBusy()
                || wheelMotors.BR.isBusy())) {
            telemetry.addData("Current WheelMotors Position",
                    "Front Left: %d\nFront Right: %d\nBack Left: %d\nBack Right: %d",
                    wheelMotors.TL.getCurrentPosition(), wheelMotors.TR.getCurrentPosition(),
                    wheelMotors.BL.getCurrentPosition(), wheelMotors.BR.getCurrentPosition());
            telemetry.update();
            Thread.sleep(1);
        }

        // Stop the Motors
        wheelMotors.setPowerAll(0);
    }

    void moveForward(final int position) throws InterruptedException {
        wheelMotors.TR.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.BL.setDirection(DcMotorSimple.Direction.REVERSE);
        // Reset Counter
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setModeAll(DcMotor.RunMode.RUN_TO_POSITION);

        wheelMotors.setTargetPositionAll(position);
        wheelMotors.setPowerAll(DRIVE_POWER);

        waitForMotors();
    }

    void moveRight(final int position) throws InterruptedException {
        wheelMotors.TR.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.BL.setDirection(DcMotorSimple.Direction.REVERSE);
        // Reset Counter
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setModeAll(DcMotor.RunMode.RUN_TO_POSITION);

        wheelMotors.setTargetPositionAll(position);

        // Set Power
        final double direction = Math.atan2(0, 1) - WheelMotors.PI_4;
        wheelMotors.setPowerAll(Math.cos(direction));

        waitForMotors();
    }

    void rotate45() throws InterruptedException {
        wheelMotors.TL.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.TR.setDirection(DcMotorSimple.Direction.REVERSE);
        // Reset Counter
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setModeAll(DcMotor.RunMode.RUN_USING_ENCODER);

        wheelMotors.setTargetPositionAll(-50);
        wheelMotors.setPowerAll(DRIVE_POWER);

        waitForMotors();
    }
}
