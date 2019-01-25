package org.firstinspires.ftc.teamcode.Berti;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@Autonomous(name = "Practice Encoders")
public class PracticeNoDetector extends LinearOpMode
{
    WheelMotors wheelMotors = new WheelMotors(hardwareMap.dcMotor);
    private double speed = 0.4;

    @Override
    public void runOpMode() throws InterruptedException
    {
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        goFront(speed);
        goFront(-speed);
        goLeft(speed);
        goLeft(-speed);
        goFront(-speed);

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    private void goFront(double setedSpeed)
    {
        wheelMotors.TR.setPower(setedSpeed);
        wheelMotors.TL.setPower(setedSpeed);
        wheelMotors.BR.setPower(setedSpeed);
        wheelMotors.BL.setPower(setedSpeed);

        sleep(1000);

        wheelMotors.TR.setPower(0);
        wheelMotors.TL.setPower(0);
        wheelMotors.BR.setPower(0);
        wheelMotors.BL.setPower(0);
    }

    private void goLeft(double setedSpeed)
    {
        wheelMotors.TR.setPower(setedSpeed);
        wheelMotors.TL.setPower(-setedSpeed);
        wheelMotors.BR.setPower(setedSpeed);
        wheelMotors.BL.setPower(-setedSpeed);

        sleep(1000);

        goFront(setedSpeed);
    }
}
