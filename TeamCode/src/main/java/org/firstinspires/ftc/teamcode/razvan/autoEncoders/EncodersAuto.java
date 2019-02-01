package org.firstinspires.ftc.teamcode.razvan.autoEncoders;

import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.GoldDetectorManager;
import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@SuppressWarnings("WeakerAccess")
public abstract class EncodersAuto extends LinearOpMode {

    private static final double DRIVE_POWER = 0.35;

    private boolean goldHit;
    private WheelMotors wheelMotors = null;
    protected Servo markerServo = null;
    private GoldDetectorManager detectorManager = null;

    @Override
    public final void runOpMode() {
        goldHit = false;
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        markerServo = hardwareMap.get(Servo.class, "Marker");

        // Start the Gold Detector
        detectorManager = new GoldDetectorManager();
        detectorManager.startDetector(hardwareMap);

        addTelemetryWithUpdate("Status", "Looking for Gold");

        detectorManager.setListener(new GoldAlignDetector.GoldAlignListener() {
            @Override
            public void onAlignChange(boolean found, boolean aligned, double lastXPos, double lastYPos) {
                if (found) {
                    telemetry.addData("Gold Status", "Last X Pos: %f, Last Y Pos: %f",
                            lastXPos, lastYPos);
                    telemetry.addData("Gold Pos", detectorManager.getLastGoldPosition().toString());
                } else {
                    telemetry.addData("Error 404", "No Gold Found");
                }
                telemetry.update();
            }
        });

        onInit();
        waitForStart();
        onStart();
    }

    protected void onInit() {

    }

    abstract protected void onStart();

    private void waitForMotors() {
        // Wait for the Motor to finish
        while (opModeIsActive()
                && wheelMotors.TL.isBusy()
                && wheelMotors.TR.isBusy()
                && wheelMotors.BL.isBusy()
                && wheelMotors.BR.isBusy()) {
            telemetry.addData("Current WheelMotors Position",
                    "\nFront Left: %d\nFront Right: %d\nBack Left: %d\nBack Right: %d",
                    wheelMotors.TL.getCurrentPosition(), wheelMotors.TR.getCurrentPosition(),
                    wheelMotors.BL.getCurrentPosition(), wheelMotors.BR.getCurrentPosition());
            telemetry.update();
            sleep(10);
        }

        sleep(100);

        // Stop the Motors
        wheelMotors.setPowerAll(0);
    }

    protected final void hitGoldIfDetected() {
        if (goldHit) return;

        sleep(600);
        GoldDetectorManager.Pos goldPos = detectorManager.getLastGoldPosition();

        if (goldPos == GoldDetectorManager.Pos.MIDDLE) {
            goldHit = true;
            moveForward(800); // TODO: Check this value
            detectorManager.stopDetector();
        }
    }

    protected final void moveForward(int position) {
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

    protected final void moveRight(int position) {
        wheelMotors.TR.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.TL.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.BR.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.BL.setDirection(DcMotorSimple.Direction.REVERSE);

        // Reset Counter
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setModeAll(DcMotor.RunMode.RUN_TO_POSITION);

        wheelMotors.setTargetPositionAll(position);
        wheelMotors.setPowerAll(DRIVE_POWER);

        waitForMotors();
    }

    /*
     * <b>Approximate values:</b>
     * 540 = 45 degrees
     * 1080 = 90 degrees
     * 2160 = 180 degrees
     */
    protected final void rotateRight(int position) {
        wheelMotors.TR.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.TL.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.BR.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.BL.setDirection(DcMotorSimple.Direction.FORWARD);

        // Reset Counter
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setModeAll(DcMotor.RunMode.RUN_TO_POSITION);

        wheelMotors.setTargetPositionAll(position);
        wheelMotors.setPowerAll(DRIVE_POWER);

        waitForMotors();
    }

    protected final void addTelemetryWithUpdate(String caption, String value) {
        telemetry.addData(caption, value);
        telemetry.update();
    }
}