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
    private int pause = 1000;

    @Override
    public void runOpMode() throws InterruptedException
    {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        telemetry.addData("Front","Run");
        telemetry.update();

        goFront(speed, pause);

        telemetry.addData("Back","Run");
        telemetry.update();

        goFront(-speed * 0.75, pause);

        telemetry.addData("Left","Run");
        telemetry.update();

        goLeft(speed * 0.75, pause);

        telemetry.addData("Right","Run");
        telemetry.update();

        goLeft(-speed * 0.75, pause);

        telemetry.addData("Front","Run");
        telemetry.update();

        goFront(speed * 0.5, pause);

        telemetry.addData("Ready","To Sleep");
        telemetry.update();

        goLeft(speed * 0.5, pause);
        goFront(-speed, pause);

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

        sleep(timeout);
    }

    private void goLeft(double setedSpeed, int timeout)
    {
        wheelMotors.TR.setPower(setedSpeed);
        wheelMotors.TL.setPower(setedSpeed);
        wheelMotors.BR.setPower(setedSpeed);
        wheelMotors.BL.setPower(setedSpeed);

        sleep(timeout);

        goFront(setedSpeed, timeout);

        sleep(timeout);
    }
}
