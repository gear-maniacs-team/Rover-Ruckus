package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Mecanum")
public class MecanumDrive extends OpMode {

    private Motors motors = null;

    @Override
    public void init()
    {
        motors = new Motors(hardwareMap.dcMotor);
    }

    @Override
    public void loop()
    {
        double rightX = gamepad1.right_stick_x;
        double leftX = gamepad1.left_stick_x;
        double leftY = -gamepad1.left_stick_y;

        double speed = Math.hypot(leftY, leftX);
        double direction = Math.atan2(leftX, leftY) - Motors.PI_4;

        double v1 = speed * Math.cos(direction) + rightX;
        double v2 = speed * Math.sin(direction) - rightX;
        double v3 = speed * Math.sin(direction) + rightX;
        double v4 = speed * Math.cos(direction) - rightX;

        motors.TL.setPower(v1);
        motors.TR.setPower(v2);
        motors.BL.setPower(v3);
        motors.BR.setPower(v4);

        telemetry.addData("Speed" , speed);
    }

    @Override
    public void stop()
    {
        //No action needed
    }
}
