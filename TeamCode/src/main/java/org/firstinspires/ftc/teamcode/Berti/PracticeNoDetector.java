package org.firstinspires.ftc.teamcode.Berti;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@Autonomous(name = "Practice Encoders")
public class PracticeNoDetector extends LinearOpMode
{
    private WheelMotors wheelMotors = null;
    private double speed = 0.4;

    @Override
    public void runOpMode() throws InterruptedException
    {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        goFront(speed);
        goFront(-speed);
        goLeft(speed);
        goLeft(-speed);
        goFront(-speed);

        sleep(200000);

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    private void goFront(double setedSpeed)
    {
        wheelMotors.TR.setPower(setedSpeed);
        wheelMotors.TL.setPower(-setedSpeed);
        wheelMotors.BR.setPower(setedSpeed);
        wheelMotors.BL.setPower(-setedSpeed);

        sleep(1000);

        wheelMotors.TR.setPower(0);
        wheelMotors.TL.setPower(0);
        wheelMotors.BR.setPower(0);
        wheelMotors.BL.setPower(0);

        sleep(1000);
    }

    private void goLeft(double setedSpeed)
    {
        wheelMotors.TR.setPower(setedSpeed);
        wheelMotors.TL.setPower(setedSpeed);
        wheelMotors.BR.setPower(setedSpeed);
        wheelMotors.BL.setPower(setedSpeed);

        sleep(1000);

        goFront(setedSpeed);

        sleep(1000);
    }
}
