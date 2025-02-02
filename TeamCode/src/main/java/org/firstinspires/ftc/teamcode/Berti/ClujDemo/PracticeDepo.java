package org.firstinspires.ftc.teamcode.Berti.ClujDemo;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@Disabled
@Autonomous(name = "Timeout_FromDepo", group = "Practice")
public class PracticeDepo extends LinearOpMode
{
    private WheelMotors wheelMotors = null;
    private double speed = 0.4;
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

        telemetry.addData("Front","Run");
        telemetry.update();

        goFront(speed*1.1, pause);

        telemetry.addData("Servo","Movement");
        telemetry.update();

        markerServo.setPosition(1);
        sleep(3000);

        telemetry.addData("Run","back and move servo back.");
        telemetry.update();

        markerServo.setPosition(0);
        goFront(-speed*0.7, pause);

        telemetry.addData("Left","Run");
        telemetry.update();

        goLeft(-speed * 0.85, pause);

        telemetry.addData("Front","Run");
        telemetry.update();

        goFront(speed, pause);

        telemetry.addData("Left","Run");
        telemetry.update();

        goLeft(-speed * 0.5, pause);

        telemetry.addData("Front","Run");
        telemetry.update();

        goFront(speed*1.9, pause);

        //Ajunge in crater

        telemetry.addData("Path", "Complete");
        telemetry.update();

        sleep(200000);
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
