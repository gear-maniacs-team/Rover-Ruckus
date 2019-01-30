package org.firstinspires.ftc.teamcode.Berti;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@Autonomous(name = "EncodersTrial", group = "Encoders")
public class EncodersTrial extends LinearOpMode
{
    private WheelMotors wheelMotors = null;
    private Servo markerServo = null;
    private double speed = 0.8;
    private int dist = 800;

    @Override
    public void runOpMode()
    {
        //Initialise motors
        initialise();

        wheelMotors.setModeAll(DcMotor.RunMode.RUN_USING_ENCODER);

        //Wait for the game start
        waitForStart();

        DriveForwardDistance(speed, dist);
        DriveBackwardDistance(speed,dist/2);
        TurnLeftDistance(speed, dist);
        DriveForwardDistance(speed,dist);
        TurnLeftDistance(speed, dist/2);
        DriveForwardDistance(speed, dist);
        DriveForwardDistance(speed, dist*2);
    }

    private void initialise()
    {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        markerServo = hardwareMap.get(Servo.class, "Marker");
    }

    private void DriveForwardDistance(double power, int distance) {
        //Reset encoders
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Set target position
        wheelMotors.setTargetPositionAll(distance);

        //Set drive power
        DriveForward(power);

        //Set to RUN_TO_POSITION mode
        wheelMotors.setModeAll(DcMotor.RunMode.RUN_TO_POSITION);

        while (wheelMotors.TR.isBusy() && wheelMotors.TR.isBusy() && wheelMotors.TL.isBusy() && wheelMotors.BR.isBusy() && wheelMotors.BL.isBusy())
        {
            //Wait until target position is reached
        }

        //Stop and change modes back to normal
        StopDriving();
    }

    private void DriveBackwardDistance(double power, int distance)
    {
        DriveForwardDistance(power, -distance);
    }

    private void TurnLeftDistance(double power, int distance)
    {
        //Reset encoders
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Set target position
        wheelMotors.TR.setTargetPosition(-distance);
        wheelMotors.TL.setTargetPosition(distance);
        wheelMotors.BR.setTargetPosition(-distance);
        wheelMotors.BL.setTargetPosition(distance);

        //Set drive power
        DriveForward(power);

        //Set to RUN_TO_POSITION mode
        wheelMotors.setModeAll(DcMotor.RunMode.RUN_TO_POSITION);

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
        DriveForward(0);
    }
}
