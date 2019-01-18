package org.firstinspires.ftc.teamcode.autonomous;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.motors.WheelMotors;

public abstract class AutonomousOp extends LinearOpMode {

    private static final double DRIVE_POWER = 0.2;
    protected GoldAlignDetector detector;
    protected WheelMotors wheelMotors = null;

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
        wheelMotors.setPower(0);
    }

    void moveForward(final int position) throws InterruptedException {
        wheelMotors.TR.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.BL.setDirection(DcMotorSimple.Direction.REVERSE);
        // Reset Counter
        wheelMotors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        wheelMotors.setTargetPosition(position);
        wheelMotors.setPower(DRIVE_POWER);

        waitForMotors();
    }

    void moveRight(final int position) throws InterruptedException {
        wheelMotors.TR.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.BL.setDirection(DcMotorSimple.Direction.REVERSE);
        // Reset Counter
        wheelMotors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        wheelMotors.setTargetPosition(position);

        // Set Power
        final double direction = Math.atan2(0, 1) - WheelMotors.PI_4;
        wheelMotors.setPower(Math.cos(direction));

        waitForMotors();
    }

    void rotate45() throws InterruptedException {
        wheelMotors.TL.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.TR.setDirection(DcMotorSimple.Direction.REVERSE);
        // Reset Counter
        wheelMotors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        wheelMotors.setTargetPosition(-50);
        wheelMotors.setPower(DRIVE_POWER);

        waitForMotors();
    }

    void startDetector() {
        // Set up detector
        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        // Optional tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

        detector.enable(); // Start the detector!
    }

    void stopDetector() {
        detector.disable();
        detector = null;
    }
}
