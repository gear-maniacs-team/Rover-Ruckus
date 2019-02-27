package org.firstinspires.ftc.teamcode.Berti.ClujDemo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@Autonomous(name = "Timeout_FromCrater", group = "Practice")
public class PracticeCrater extends LinearOpMode
{
    private WheelMotors wheelMotors = null;
    private final double speed = 0.4;
    private int pause = 1000;
    private Servo markerServo = null;

    @Override
    public void runOpMode()
    {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        markerServo = hardwareMap.get(Servo.class, "Marker");
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        goFront(speed*0.7, pause);

        goFront(-speed*0.8, pause/2);

        goLeft(-speed * 1.1, pause/2);

        goFront(speed*1.1, pause);

        goLeft(-speed*0.75, pause);

        goFront(speed*0.9, pause*2);

        //servo
        markerServo.setPosition(1);

        sleep(pause*3);

        markerServo.setPosition(0);

        goFront(-speed * 2.5, pause + 200);

        //servo

        sleep(200000);

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    private void goFront(double setedSpeed, int timeout)
    {
        wheelMotors.TR.setPower(-setedSpeed);
        wheelMotors.TL.setPower(setedSpeed);
        wheelMotors.BR.setPower(-setedSpeed);
        wheelMotors.BL.setPower(setedSpeed);

        sleep(timeout);

        wheelMotors.TR.setPower(0);
        wheelMotors.TL.setPower(0);
        wheelMotors.BR.setPower(0);
        wheelMotors.BL.setPower(0);

        sleep(timeout/2);
    }

    private void goLeft(double setedSpeed, int timeout)
    {
        wheelMotors.TR.setPower(setedSpeed);
        wheelMotors.TL.setPower(setedSpeed);
        wheelMotors.BR.setPower(setedSpeed);
        wheelMotors.BL.setPower(setedSpeed);

        sleep(timeout);

        wheelMotors.TR.setPower(0);
        wheelMotors.TL.setPower(0);
        wheelMotors.BR.setPower(0);
        wheelMotors.BL.setPower(0);

        sleep(timeout/2);
    }
}
