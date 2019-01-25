package org.firstinspires.ftc.teamcode.Berti;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "Concept: TensorFlow Object Detection", group = "Concept")
public class DetectorVuforiaTest extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "AZnVnoj/////AAABmdXzVSC7bkZik9EURkca9g5GwHTQjL0SB5CABkSEajM1oX/nSOWoXxcxH/watnjKf3WlWcGhyPvV0E8eMNZmTbTgrB/8OJhqAflMV+CjgBtERmweuXjLiPcvEgJNrZD7USn+LK53L0VuSYdi4NwJxy7ypbse7jbXlOmJVgogCXbD4+yjYDbnVmBkkMQMhLgIFQZ0wRApvdxc7R/O/rhsQfWrWWekxjIp4wNeYh5JBsCrCRjdPu1P7QLKAMSOpK5lXqJjmD36TPDxqrQEGfdKxkMe2SJta/3tyzc+v/mFRmNDJjqVMYu69eEy6jh7u/KQA2Uj4pdcIfnZhMWwBO58guP2TPl5HCof4weEEUI6ZF8w";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private DcMotor frontleft;
    private DcMotor frontright;
    private DcMotor backleft;
    private DcMotor backright;
    //private DcMotor motorarm3;

    private double speedModerate = 0.5;

    @Override
    public void runOpMode() {

        frontleft = hardwareMap.dcMotor.get("TopLeft");
        frontright = hardwareMap.dcMotor.get("TopRight");
        backleft = hardwareMap.dcMotor.get("BackLeft");
        backright = hardwareMap.dcMotor.get("BackLeft");
        //motorarm3 = hardwareMap.dcMotor.get("motorarm3");

        frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
        backleft.setDirection(DcMotorSimple.Direction.REVERSE);

        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        DriveForward(0);

        if (opModeIsActive()) {
            if (tfod != null) {
                tfod.activate();
            }
            while (opModeIsActive()) {
                if (tfod != null) {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 3) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getBottom();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getBottom();
                                } else {
                                    silverMineral2X = (int) recognition.getBottom();
                                }
                            }

                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    goRight();
                                    telemetry.addData("Gold MIneral Position is", "Right");
                                    telemetry.update();
                                    break;
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    goLeft();
                                    telemetry.addData("Gold Mineral Position is", "Left");
                                    telemetry.update();
                                    break;
                                } else {
                                    goFront();
                                    telemetry.addData("Gold Mineral Position is","Center");
                                    telemetry.update();
                                    break;
                                }


                            }
                        }
                        telemetry.update();
                        sleep(10000);
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
        afterDetection();
    }

    private void goFront() {
        frontleft.setPower(0.2);
        frontright.setPower(0.2);
        backleft.setPower(0.2);
        backright.setPower(0.2);

        sleep(2000);

        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);
        afterDetection();
    }

    private void goLeft() {
        frontleft.setPower(-0.2);
        frontright.setPower(0.2);
        backleft.setPower(-0.2);
        backright.setPower(-0.2);

        sleep(1000);

        frontleft.setPower(0.2);
        frontright.setPower(0.2);
        backleft.setPower(0.2);
        backright.setPower(0.2);

        sleep(2000);

        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);
        afterDetection();
    }

    private void goRight() {
        frontleft.setPower(0.2);
        frontright.setPower(-0.2);
        backleft.setPower(0.2);
        backright.setPower(-0.2);

        sleep(1000);

        frontleft.setPower(0.2);
        frontright.setPower(0.2);
        backleft.setPower(0.2);
        backright.setPower(0.2);

        sleep(2000);

        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);

        afterDetection();
    }
    private void afterDetection() {

    }
    private void DriveForward(double speed) {
        frontleft.setPower(speed);
        frontright.setPower(speed);
        backleft.setPower(speed);
        backright.setPower(speed);

    }
    private void drive(double frontleftspeed,double frontrightspeed,double backleftspeed,double backrightspeed) {
        frontleft.setPower(frontleftspeed);
        frontright.setPower(frontrightspeed);
        backleft.setPower(backleftspeed);
        backright.setPower(backrightspeed);
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
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}