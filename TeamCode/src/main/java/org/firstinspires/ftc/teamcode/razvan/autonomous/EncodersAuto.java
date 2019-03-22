package org.firstinspires.ftc.teamcode.razvan.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.ExceptionHandler;
import org.firstinspires.ftc.teamcode.motors.ArmMotors;
import org.firstinspires.ftc.teamcode.motors.WheelMotors;
import org.firstinspires.ftc.teamcode.razvan.VuforiaManager;

@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
public abstract class EncodersAuto extends LinearOpMode {

    private static final double DEFAULT_DRIVE_POWER = 0.6;

    private final ExceptionHandler exceptionHandler = new ExceptionHandler();
    private final VuforiaManager vuforiaManager = new VuforiaManager();
    private WheelMotors wheelMotors = null;
    private ArmMotors armMotors = null;

    //public final static double CIRC = 31.9024;
    //public final static double TICKS_PER_ROTATION = 1120;
    //public final static double TICKS = CIRC * TICKS_PER_ROTATION;

    @Override
    public final void runOpMode() {
        exceptionHandler.clear();
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        armMotors = new ArmMotors(hardwareMap.dcMotor);

        vuforiaManager.startDetectorAsync(hardwareMap);

        waitForStart();
        try {
            onStart();
        } catch (Exception e) {
            exceptionHandler.parseException(e);
            exceptionHandler.writeToFile(true, "Autonomous-Encoder");
            exceptionHandler.clear();
            throw e;
        }

        addTelemetryWithUpdate("Status", "Path Completed");
    }

    abstract protected void onStart();

    protected void addTelemetryWithUpdate(String caption, String value) {
        telemetry.addData(caption, value);
        telemetry.update();
    }

    //region Detector

    protected final boolean detectAndHitGold() {
        boolean goldHit = false;

        sleep(500);
        if (vuforiaManager.searchForGold()) {
            goldHit = true;
            hitGold();
        }

        return goldHit;
    }

    protected final void hitGold() {
        moveRight(850, 0.4);
        moveRight(-850);
        vuforiaManager.stopCamera();
    }

    protected final void waitForDetector() {
        vuforiaManager.waitForDetector();
    }

    protected final void stopCamera() {
        vuforiaManager.stopCamera();
    }

    //endregion Detector

    //region Motors

    protected final void lowerRobot() {
        // Starting and resetting counter
        armMotors.latchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotors.latchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        armMotors.latchMotor.setTargetPosition(-12200);
        armMotors.latchMotor.setPower(1);

        // Wait for the Latching Motor to finish
        while (armMotors.latchMotor.isBusy()) {
            telemetry.addData("Current Latching Position",
                    armMotors.latchMotor.getCurrentPosition());
            telemetry.update();
            sleep(10);
        }

        sleep(500);

        armMotors.latchMotor.setPower(0);
        moveRight(-100, 0.2);
    }

    protected final void deployMarker() {
        armMotors.collector.setPower(-1);
        sleep(600);
        armMotors.collector.setPower(0);
    }

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

    protected final void lowerArm() {
        armMotors.armAngle.setPower(-0.45);
        sleep(900);
    }

    protected final void moveForward(int position) {
        moveForward(position, DEFAULT_DRIVE_POWER);
    }

    protected final void moveForward(int position, double power) {
        wheelMotors.TR.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.TL.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.BR.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.BL.setDirection(DcMotorSimple.Direction.FORWARD);
        // Reset Counter
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setModeAll(DcMotor.RunMode.RUN_TO_POSITION);

        wheelMotors.setTargetPositionAll(position);
        wheelMotors.setPowerAll(power);

        waitForMotors();
    }

    protected final void moveRight(int position) {
        moveRight(position, DEFAULT_DRIVE_POWER);
    }

    protected final void moveRight(int position, double power) {
        wheelMotors.TR.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.TL.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.BR.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.BL.setDirection(DcMotorSimple.Direction.REVERSE);

        // Reset Counter
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setModeAll(DcMotor.RunMode.RUN_TO_POSITION);

        wheelMotors.setTargetPositionAll(position);
        wheelMotors.setPowerAll(power);

        waitForMotors();
    }

    protected final void rotateRight(int position) {
        rotateRight(position, DEFAULT_DRIVE_POWER);
    }

    /*
     * Approximate values:
     * 540 = 45 degrees
     * 1080 = 90 degrees
     * 2160 = 180 degrees
     */
    protected final void rotateRight(int position, double power) {
        wheelMotors.TR.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.TL.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.BR.setDirection(DcMotorSimple.Direction.FORWARD);
        wheelMotors.BL.setDirection(DcMotorSimple.Direction.FORWARD);

        // Reset Counter
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set WheelMotors to run to target position
        wheelMotors.setModeAll(DcMotor.RunMode.RUN_TO_POSITION);

        wheelMotors.setTargetPositionAll(position);
        wheelMotors.setPowerAll(power);

        waitForMotors();
    }

    //endregion Motors

    //region Path

    protected final void movementWithSampling() {
        moveForward(-200);
        moveRight(1200, 0.5);
        moveForward(200);

        sampling();
    }

    private void sampling() {
        int dist = 1600;
        int restDist;

        if (detectAndHitGold()) {
            restDist = 1600 - dist;
            moveForward(dist + restDist + 600);
        } else {
            moveForward(-700);
            if (detectAndHitGold()) {
                restDist = dist - 700;
                moveForward(dist + restDist + 450);
            } else {
                moveForward(dist);

                hitGold();

                restDist = 800 - dist;
                moveForward(dist + restDist + 500);
            }
        }
    }

    //endregion Path
}
