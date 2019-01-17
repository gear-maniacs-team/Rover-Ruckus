package org.firstinspires.ftc.teamcode.autonomous;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Motors;

public abstract class AutonomousOp extends LinearOpMode {

    protected GoldAlignDetector detector;
    protected Motors motors = null;

    @Override
    public void runOpMode() throws InterruptedException {
        motors = new Motors(hardwareMap.dcMotor);
    }

    private void waitForMotors() {
        // Wait for the Motor to stop
        while (opModeIsActive()
                && motors.TL.isBusy()
                && motors.TR.isBusy()
                && motors.BL.isBusy()
                && motors.BR.isBusy()) {
            telemetry.addData("Pos", "Front: %d, %d Back: %d, %d",
                    motors.TL.getCurrentPosition(), motors.TR.getCurrentPosition(),
                    motors.BL.getCurrentPosition(), motors.BR.getCurrentPosition());
            telemetry.update();
            idle();
        }

        // Stop the Motors
        motors.TL.setPower(0);
        motors.TR.setPower(0);
        motors.BL.setPower(0);
        motors.BR.setPower(0);
    }

    void moveForward(final int position) {
        // Reset Counter
        motors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set Motors to run to target position
        motors.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motors.TL.setTargetPosition(position);
        motors.TR.setTargetPosition(-position);
        motors.BL.setTargetPosition(-position);
        motors.BR.setTargetPosition(position);

        // Set Power
        motors.TL.setPower(0.5);
        motors.TR.setPower(-0.5);
        motors.BL.setPower(-0.5);
        motors.BR.setPower(0.5);

        waitForMotors();
    }

    void moveRight(final int position) {
        motors.TR.setDirection(DcMotorSimple.Direction.REVERSE);
        motors.BL.setDirection(DcMotorSimple.Direction.REVERSE);
        // Reset Counter
        motors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set Motors to run to target position
        motors.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motors.TL.setTargetPosition(position);
        motors.TR.setTargetPosition(-position);
        motors.BL.setTargetPosition(-position);
        motors.BR.setTargetPosition(position);

        // Set Power
        final double direction = Math.atan2(0, 1) - Motors.PI_4;
        final double v1 = Math.cos(direction);

        motors.TL.setPower(v1);
        motors.TR.setPower(v1);
        motors.BL.setPower(v1);
        motors.BR.setPower(v1);

        waitForMotors();
    }

    void rotate45() {
        // Reset Counter
        motors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set Motors to run to target position
        motors.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motors.TL.setTargetPosition(-10);
        motors.TR.setTargetPosition(-10);
        motors.BL.setTargetPosition(10);
        motors.BR.setTargetPosition(10);

        motors.TL.setPower(-1);
        motors.TR.setPower(-1);
        motors.BL.setPower(1);
        motors.BR.setPower(1);

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
