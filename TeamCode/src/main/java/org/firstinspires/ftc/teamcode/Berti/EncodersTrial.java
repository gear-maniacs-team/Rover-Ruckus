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
    private double power = 0.4;
    private double dist_tile = 24.5;
    private double circumference = 4 * Math.PI;
    private double rotationsNeeded = dist_tile / circumference;
    private static final int TICK_COUNTS_PER_MOTOR = 1120;
    private int drivingTarget = (int)rotationsNeeded * TICK_COUNTS_PER_MOTOR;

    @Override
    public void runOpMode()
    {
        //Initialise motors
        initialise();

        wheelMotors.setModeAll(DcMotor.RunMode.RUN_USING_ENCODER);

        //Wait for the game start
        waitForStart();

        DriveForwardDistance(drivingTarget*3/4);
        DriveBackwardDistance(drivingTarget/2);
        TurnLeftDistance(drivingTarget/2);
        DriveForwardDistance(drivingTarget);
        TurnLeftDistance(drivingTarget/2);
        DriveForwardDistance(drivingTarget);
        DriveForwardDistance(drivingTarget*2);
    }

    private void initialise()
    {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        markerServo = hardwareMap.get(Servo.class, "Marker");
    }

    private void DriveForwardDistance(int distance)
    {
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

    private void DriveBackwardDistance(int distance)
    {
        DriveForwardDistance(-distance);
    }

    private void TurnLeftDistance(int distance)
    {
        //Reset encoders
        wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Set target position
        wheelMotors.TR.setTargetPosition(distance);
        wheelMotors.TL.setTargetPosition(distance);
        wheelMotors.BR.setTargetPosition(distance);
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
        wheelMotors.TR.setPower(-power);
        wheelMotors.TL.setPower(power);
        wheelMotors.BR.setPower(-power);
        wheelMotors.BL.setPower(power);
    }

    private void StopDriving()
    {
        DriveForward(0);
    }
}
