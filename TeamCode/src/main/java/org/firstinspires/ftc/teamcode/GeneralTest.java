package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@Disabled
@TeleOp(name = "OpMode", group = "Test")
public class GeneralTest extends LinearOpMode {

    private WheelMotors wheelMotors = null;
    private DcMotor armMotor = null;

    @Override
    public void runOpMode() throws InterruptedException {
        initialise();

        waitForStart();

        while(opModeIsActive()) {
            double leftY = gamepad1.left_stick_y;

            armMotor.setPower(leftY);
        }
    }

    private void initialise() {
        //wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        armMotor = hardwareMap.dcMotor.get("armMotor");
    }
}
