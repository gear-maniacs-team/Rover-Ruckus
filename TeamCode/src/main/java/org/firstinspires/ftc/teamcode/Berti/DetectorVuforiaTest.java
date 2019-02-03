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
import org.firstinspires.ftc.teamcode.motors.WheelMotors;

import java.util.List;

@Autonomous(name = "Concept: TensorFlow Object Detection", group = "Concept")
public class DetectorVuforiaTest extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "AZnVnoj/////AAABmdXzVSC7bkZik9EURkca9g5GwHTQjL0SB5CABkSEajM1oX/nSOWoXxcxH/watnjKf3WlWcGhyPvV0E8eMNZmTbTgrB/8OJhqAflMV+CjgBtERmweuXjLiPcvEgJNrZD7USn+LK53L0VuSYdi4NwJxy7ypbse7jbXlOmJVgogCXbD4+yjYDbnVmBkkMQMhLgIFQZ0wRApvdxc7R/O/rhsQfWrWWekxjIp4wNeYh5JBsCrCRjdPu1P7QLKAMSOpK5lXqJjmD36TPDxqrQEGfdKxkMe2SJta/3tyzc+v/mFRmNDJjqVMYu69eEy6jh7u/KQA2Uj4pdcIfnZhMWwBO58guP2TPl5HCof4weEEUI6ZF8w";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private WheelMotors wheelMotors = null;
    private static final double COUNTS_PER_MOTOR_REV = 1120;
    private static final double DRIVE_GEAR_REDUCTION = 2.0;
    private static final double WHEEL_DIAMETER_INCHES = 4.0;
    private static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV*DRIVE_GEAR_REDUCTION)/(WHEEL_DIAMETER_INCHES*Math.PI);

    private double speedModerate = 0.5;
    private int distanceTile = 24;

    @Override
    public void runOpMode() {

        initVuforia();
        initMotors();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        wheelMotors.setModeAll(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        if (opModeIsActive())
        {
            if (tfod != null)
            {
                tfod.activate();
            }
            while (opModeIsActive())
            {
                if (tfod != null)
                {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null)
                    {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 3)
                        {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions)
                            {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL))
                                {
                                    goldMineralX = (int) recognition.getBottom();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getBottom();
                                } else {
                                    silverMineral2X = (int) recognition.getBottom();
                                }
                            }

                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1)
                            {
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X)
                                {
                                    telemetry.addData("Gold Mineral Position is", "Right");
                                    telemetry.update();
                                    break;
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position is", "Left");
                                    telemetry.update();
                                    break;
                                } else {
                                    Center();
                                    telemetry.addData("Gold Mineral Position is","Center");
                                    telemetry.update();
                                    break;
                                }

                            }
                        }
                        telemetry.addData("Robot is going","to sleep.");
                        telemetry.update();
                        sleep(10000);
                    }
                }
            }
        }

        if (tfod != null)
        {
            tfod.shutdown();
        }
        afterDetection();
    }

    private void initMotors()
    {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);

        wheelMotors.TL.setDirection(DcMotorSimple.Direction.REVERSE);
        wheelMotors.BL.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void Center()
    {
        DriveRightDistance(distanceTile);
        DriveRightDistance(-distanceTile/2);
        DriveForwardDistance(distanceTile*2);
        TurnRightDistance(distanceTile);
        DriveRightDistance(distanceTile/4);
        DriveForwardDistance(-distanceTile);
        DriveForwardDistance(distanceTile*4);
    }

    private void DriveForwardDistance(int distance)
    {
        //Reset encoders
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Set target position
        wheelMotors.TR.setTargetPosition(wheelMotors.TR.getCurrentPosition() + (int)(distance*COUNTS_PER_INCH));
        wheelMotors.TL.setTargetPosition(wheelMotors.TL.getCurrentPosition() + (int)(distance*COUNTS_PER_INCH));
        wheelMotors.BR.setTargetPosition(wheelMotors.BR.getCurrentPosition() + (int)(distance*COUNTS_PER_INCH));
        wheelMotors.BL.setTargetPosition(wheelMotors.BL.getCurrentPosition() + (int)(distance*COUNTS_PER_INCH));

        //Set drive power
        DriveForward(speedModerate);

        //Set to RUN_TO_POSITION mode
        wheelMotors.TR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wheelMotors.TL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wheelMotors.BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wheelMotors.BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (wheelMotors.TR.isBusy() && wheelMotors.TR.isBusy() && wheelMotors.TL.isBusy() && wheelMotors.BR.isBusy() && wheelMotors.BL.isBusy())
        {
            //Wait until target position is reached
        }

        //Stop and change modes back to normal
        StopDriving();
    }

    private void DriveRightDistance(int distance)
    {
        //Reset encoders
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Set target position
        wheelMotors.TR.setTargetPosition(wheelMotors.TR.getCurrentPosition() + (int)(distance*COUNTS_PER_INCH));
        wheelMotors.TL.setTargetPosition(wheelMotors.TL.getCurrentPosition() + (int)(distance*COUNTS_PER_INCH));
        wheelMotors.BR.setTargetPosition(wheelMotors.BR.getCurrentPosition() + (int)(distance*COUNTS_PER_INCH));
        wheelMotors.BL.setTargetPosition(wheelMotors.BL.getCurrentPosition() + (int)(distance*COUNTS_PER_INCH));

        //Set drive power
        wheelMotors.TR.setPower(-speedModerate);
        wheelMotors.TL.setPower(speedModerate);
        wheelMotors.BR.setPower(speedModerate);
        wheelMotors.BL.setPower(-speedModerate);

        //Set to RUN_TO_POSITION mode
        wheelMotors.TR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wheelMotors.TL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wheelMotors.BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wheelMotors.BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (wheelMotors.TR.isBusy() && wheelMotors.TR.isBusy() && wheelMotors.TL.isBusy() && wheelMotors.BR.isBusy() && wheelMotors.BL.isBusy())
        {
            //Wait until target position is reached
        }

        //Stop and change modes back to normal
        StopDriving();
    }

    private void TurnRightDistance(int distance)
    {
        //Reset encoders
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Set target position
        wheelMotors.TR.setTargetPosition(wheelMotors.TR.getCurrentPosition() + (int)(distance*COUNTS_PER_INCH));
        wheelMotors.TL.setTargetPosition(wheelMotors.TL.getCurrentPosition() + (int)(distance*COUNTS_PER_INCH));
        wheelMotors.BR.setTargetPosition(wheelMotors.BR.getCurrentPosition() + (int)(distance*COUNTS_PER_INCH));
        wheelMotors.BL.setTargetPosition(wheelMotors.BL.getCurrentPosition() + (int)(distance*COUNTS_PER_INCH));

        //Set drive power
        wheelMotors.TR.setPower(speedModerate);
        wheelMotors.TL.setPower(-speedModerate);
        wheelMotors.BR.setPower(speedModerate);
        wheelMotors.BL.setPower(-speedModerate);

        //Set to RUN_TO_POSITION mode
        wheelMotors.TR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wheelMotors.TL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wheelMotors.BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wheelMotors.BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(wheelMotors.TR.isBusy() && wheelMotors.TR.isBusy() && wheelMotors.TL.isBusy() && wheelMotors.BR.isBusy() && wheelMotors.BL.isBusy())
        {
            //Wait until target position is reached
        }

        //Stop and change modes back to normal
        StopDriving();
    }

    private void DriveForward(double power)
    {
        wheelMotors.TR.setPower(power);
        wheelMotors.TL.setPower(power);
        wheelMotors.BR.setPower(power);
        wheelMotors.BL.setPower(power);
    }

    private void StopDriving()
    {
        wheelMotors.TR.setPower(0);
        wheelMotors.TL.setPower(0);
        wheelMotors.BR.setPower(0);
        wheelMotors.BL.setPower(0);
    }

    private void afterDetection()
    {

    }

    private void initVuforia()
    {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;


        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod()
    {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}