package org.firstinspires.ftc.teamcode.Berti;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@Autonomous(name = "Practice Encoders")
public class PracticeNoDetector extends LinearOpMode
{
    WheelMotors wheelMotors = new WheelMotors(hardwareMap.dcMotor);
    private double speed = 0.5;

    @Override
    public void runOpMode() throws InterruptedException
    {
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        goFront();

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    private void goFront() {
        wheelMotors.TR.setPower(speed);
        wheelMotors.TL.setPower(speed);
        wheelMotors.BR.setPower(speed);
        wheelMotors.BL.setPower(speed);

        sleep(1000);

        wheelMotors.TR.setPower(0);
        wheelMotors.TL.setPower(0);
        wheelMotors.BR.setPower(0);
        wheelMotors.BL.setPower(0);
    }
}
