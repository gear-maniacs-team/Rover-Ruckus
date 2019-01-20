package org.firstinspires.ftc.teamcode.Berti;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Proba")
public class AutonomousBlue extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareTest robot   = new HardwareTest();

    @Override
    public void runOpMode()
    {

        //initialise robot with all motors
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.wheelMotors.setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.wheelMotors.setModeAll(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        robot.wheelMotors.TR.setTargetPosition(27);
        robot.wheelMotors.TL.setTargetPosition(27);
        robot.wheelMotors.BR.setTargetPosition(27);
        robot.wheelMotors.BL.setTargetPosition(27);

        robot.wheelMotors.TR.setPower(0.2);
        robot.wheelMotors.TL.setPower(0.2);
        robot.wheelMotors.BR.setPower(0.2);
        robot.wheelMotors.BL.setPower(0.2);

        while(robot.wheelMotors.TR.isBusy() && robot.wheelMotors.TL.isBusy() && robot.wheelMotors.BR.isBusy() && robot.wheelMotors.BL.isBusy())
        {

        }

        robot.wheelMotors.setPowerAll(0);


    }
}
