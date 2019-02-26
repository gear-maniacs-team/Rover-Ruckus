package org.firstinspires.ftc.teamcode.razvan.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.ExceptionHandler;
import org.firstinspires.ftc.teamcode.motors.ArmMotors;
import org.firstinspires.ftc.teamcode.motors.WheelMotors;

import java.util.List;

@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
public abstract class EncodersAuto extends LinearOpMode {

    private static final double DEFAULT_DRIVE_POWER = 0.4;
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "AZnVnoj/////AAABmdXzVSC7bkZik9EURkca9g5GwHTQjL0SB5CABkSEajM1oX/nSOWoXxcxH/watnjKf3WlWcGhyPvV0E8eMNZmTbTgrB/8OJhqAflMV+CjgBtERmweuXjLiPcvEgJNrZD7USn+LK53L0VuSYdi4NwJxy7ypbse7jbXlOmJVgogCXbD4+yjYDbnVmBkkMQMhLgIFQZ0wRApvdxc7R/O/rhsQfWrWWekxjIp4wNeYh5JBsCrCRjdPu1P7QLKAMSOpK5lXqJjmD36TPDxqrQEGfdKxkMe2SJta/3tyzc+v/mFRmNDJjqVMYu69eEy6jh7u/KQA2Uj4pdcIfnZhMWwBO58guP2TPl5HCof4weEEUI6ZF8w";

    private VuforiaLocalizer vuforia = null;
    private TFObjectDetector tfod = null;

    private final ExceptionHandler exceptionHandler = new ExceptionHandler();
    private WheelMotors wheelMotors = null;
    private ArmMotors armMotors = null;
    //protected Servo markerServo = null;

    public final static double CIRC = 31.9024;
    public final static double TICKS_PER_ROTATION = 1120;
    public final static double TICKS = CIRC * TICKS_PER_ROTATION;

    @Override
    public final void runOpMode() {
        exceptionHandler.clear();
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        armMotors = new ArmMotors(hardwareMap.dcMotor);
        //markerServo = hardwareMap.get(Servo.class, "Marker");

        startDetector();

        waitForStart();
        try {
            onStart();
        } catch (Exception e) {
            exceptionHandler.parseException(e);
            exceptionHandler.writeToFile(true, "Autonomous-Encoder");
            exceptionHandler.clear();
            throw e;
        }

        //lowerArm();

        addTelemetryWithUpdate("Status", "Path Completed");
    }

    abstract protected void onStart();

    //region Detector

    private void startDetector() {
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            addTelemetryWithUpdate("Sorry!", "This device is not compatible with TFOD");
        }
    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParams = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParams, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    private boolean searchForGold() {
        if (tfod != null)
            tfod.activate();

        boolean goldHit = false;

        sleep(800);

        if (tfod != null) {
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                for (Recognition recognition : updatedRecognitions) {
                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                        goldHit = true;
                        telemetry.addData("# Found", LABEL_GOLD_MINERAL);
                    }
                }
            }
        }
        telemetry.update();

        // if (tfod != null) {
        //    tfod.shutdown();
        // }

        return goldHit;
    }

    protected final boolean hitGoldIfDetected() {
        boolean goldHit = false;

        if (searchForGold()) {
            goldHit = true;

            moveRight(800);
            sleep(300);

            moveRight(-800);
            sleep(300);
        }

        return goldHit;
    }

    public void stopCamera() {
        tfod.shutdown();
    }

    //endregion Detector

    //region Motors

    // Latching Motor
    protected void lowerRobot() {
        // Starting and resetting counter
        armMotors.latchMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotors.latchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        armMotors.latchMotor.setTargetPosition(100);
        armMotors.latchMotor.setPower(1);

        addTelemetryWithUpdate("Status", "Lowering robot");

        while (armMotors.latchMotor.isBusy()) {
            telemetry.addData("Current Latching Position",
                    armMotors.latchMotor.getController());
            telemetry.update();
            sleep(10);
        }

        sleep(100);

        armMotors.latchMotor.setPower(0);
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

    private void waitForArms() {
        // Wait for the Arms to finish
        while (armMotors.armAngle.isBusy() && armMotors.armExtension.isBusy()) {
            telemetry.addData("Current Arm Position",
                    "\nArm Angle: %d\nArm Extension: %d",
                    armMotors.armAngle.getCurrentPosition(), armMotors.armAngle.getCurrentPosition());
            telemetry.update();
            sleep(10);
        }

        sleep(100);

        // Stop the Motors
        armMotors.armAngle.setPower(0);
        armMotors.armExtension.setPower(0);
    }

    protected void lowerArm() {
        armMotors.armAngle.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotors.armAngle.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotors.armExtension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotors.armExtension.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        armMotors.armAngle.setPower(0);
        waitForArms();
        armMotors.armExtension.setPower(0);
        waitForArms();
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

    protected final void addTelemetryWithUpdate(String caption, String value) {
        telemetry.addData(caption, value);
        telemetry.update();
    }

    //endregion Motors
}