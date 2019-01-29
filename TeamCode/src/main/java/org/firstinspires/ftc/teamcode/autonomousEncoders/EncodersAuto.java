package org.firstinspires.ftc.teamcode.autonomousEncoders;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.GoldDetectorManager;
import org.firstinspires.ftc.teamcode.motors.WheelMotors;

public abstract class EncodersAuto extends LinearOpMode {

    protected static final double DRIVE_POWER = 0.4;
    protected WheelMotors wheelMotors = null;
    protected GoldDetectorManager detectorManager = null;

    @Override
    public void runOpMode() {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        detectorManager = new GoldDetectorManager();
    }

    private void waitForMotors() {
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
            sleep(5);
        }

        // Stop the Motors
        wheelMotors.setPowerAll(0);
    }

    protected void moveForward(int position) {
        wheelMotors.TR.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.TL.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.BR.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.BL.setDirection(DcMotorSimple.Direction.FORWARD);
        // Reset Counter
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setModeAll(DcMotor.RunMode.RUN_TO_POSITION);

        wheelMotors.setTargetPositionAll(position);
        wheelMotors.setPowerAll(DRIVE_POWER);

        waitForMotors();
    }

    protected void moveRight(final int position) {
        wheelMotors.TR.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.TL.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.BR.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.BL.setDirection(DcMotorSimple.Direction.REVERSE);
        /*wheelMotors.TR.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.TL.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.BR.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.BL.setDirection(DcMotorSimple.Direction.FORWARD);*/

        // Reset Counter
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setModeAll(DcMotor.RunMode.RUN_TO_POSITION);

        wheelMotors.setTargetPositionAll(position);
        wheelMotors.setPowerAll(DRIVE_POWER);

        waitForMotors();
    }

    protected void rotate(int position) {
        // Reset Counter
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setModeAll(DcMotor.RunMode.RUN_USING_ENCODER);


        // TODO: Set Target Position
        wheelMotors.setPowerAll(DRIVE_POWER);

        waitForMotors();
    }

    protected void addTelemetryWithUpdate(String caption, String value) {
        telemetry.addData(caption, value);
        telemetry.update();
    }
}