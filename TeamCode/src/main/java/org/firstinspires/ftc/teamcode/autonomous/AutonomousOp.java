package org.firstinspires.ftc.teamcode.autonomous;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.motors.WheelMotors;

public abstract class AutonomousOp extends LinearOpMode {

    protected GoldAlignDetector detector;
    protected WheelMotors wheelMotors = null;

    @Override
    public void runOpMode() throws InterruptedException {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
    }

    private void waitForMotors() throws InterruptedException {
        // Wait for the Motor to stop
        while (opModeIsActive()
                && (wheelMotors.TL.isBusy()
                || wheelMotors.TR.isBusy()
                || wheelMotors.BL.isBusy()
                || wheelMotors.BR.isBusy())) {
            telemetry.addData("Current WheelMotors Position", "Front: %d, %d Back: %d, %d",
                    wheelMotors.TL.getCurrentPosition(), wheelMotors.TR.getCurrentPosition(),
                    wheelMotors.BL.getCurrentPosition(), wheelMotors.BR.getCurrentPosition());
            telemetry.update();
            Thread.sleep(1);
        }

        // Stop the WheelMotors
        wheelMotors.TL.setPower(0);
        wheelMotors.TR.setPower(0);
        wheelMotors.BL.setPower(0);
        wheelMotors.BR.setPower(0);
    }

    void moveForward(final int position) throws InterruptedException {
        // Reset Counter
        wheelMotors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        wheelMotors.TL.setTargetPosition(position);
        wheelMotors.TR.setTargetPosition(-position);
        wheelMotors.BL.setTargetPosition(-position);
        wheelMotors.BR.setTargetPosition(position);

        // Set Power
        wheelMotors.TL.setPower(0.5);
        wheelMotors.TR.setPower(-0.5);
        wheelMotors.BL.setPower(-0.5);
        wheelMotors.BR.setPower(0.5);

        waitForMotors();
    }

    void moveRight(final int position) throws InterruptedException {
        wheelMotors.TR.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.BL.setDirection(DcMotorSimple.Direction.REVERSE);
        // Reset Counter
        wheelMotors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        wheelMotors.TL.setTargetPosition(position);
        wheelMotors.TR.setTargetPosition(-position);
        wheelMotors.BL.setTargetPosition(-position);
        wheelMotors.BR.setTargetPosition(position);

        // Set Power
        final double direction = Math.atan2(0, 1) - WheelMotors.PI_4;
        final double v1 = Math.cos(direction);

        wheelMotors.TL.setPower(v1);
        wheelMotors.TR.setPower(v1);
        wheelMotors.BL.setPower(v1);
        wheelMotors.BR.setPower(v1);

        waitForMotors();
    }

    void rotate45() throws InterruptedException {
        // Reset Counter
        wheelMotors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        wheelMotors.TL.setTargetPosition(-10);
        wheelMotors.TR.setTargetPosition(-10);
        wheelMotors.BL.setTargetPosition(10);
        wheelMotors.BR.setTargetPosition(10);

        wheelMotors.TL.setPower(-1);
        wheelMotors.TR.setPower(-1);
        wheelMotors.BL.setPower(1);
        wheelMotors.BR.setPower(1);

        waitForMotors();
    }

    @Override
    public void updateTelemetry(Telemetry telemetry) {
        telemetry.addData("IsAligned", detector.getAligned()); // Is the bot aligned with the gold mineral?
        telemetry.addData("X Pos", detector.getXPosition()); // Gold X position.
        telemetry.addData("Y Pos", detector.getYPosition()); // Gold Y position.
        telemetry.update();
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
