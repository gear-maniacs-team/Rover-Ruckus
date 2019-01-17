package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Mecanum")
public class MecanumDrive extends OpMode {

    private Motors motors = null;

    @Override
    public void init() { motors = new Motors(hardwareMap.dcMotor); }

    @Override
    public void loop() {
        double leftX = gamepad1.left_stick_x;
        double leftY = -gamepad1.left_stick_y;

        double speed = Math.hypot(leftY, leftX);
        double direction = Math.atan2(leftX, leftY) - Motors.PI_4;

        double v1 = speed * Math.cos(direction);
        double v2 = speed * Math.sin(direction);

        motors.TL.setPower(v1 * 0.75);
        motors.TR.setPower(v2 * 0.75);
        motors.BL.setPower(v2 * 0.75);
        motors.BR.setPower(v1 * 0.75);

        while(gamepad1.dpad_up)
            motors.AE.setPower(0.2);
        while(gamepad1.dpad_down)
            motors.AE.setPower(-0.2);
        motors.AE.setPower(0);

        while(gamepad1.right_stick_x > 0)
        {
            motors.TR.setPower(0.3);
            motors.TL.setPower(0.3);
            motors.BL.setPower(-0.3);
            motors.BR.setPower(-0.3);
        }
        while(gamepad1.right_stick_x < 0)
        {
            motors.TR.setPower(-0.3);
            motors.TL.setPower(-0.3);
            motors.BL.setPower(0.3);
            motors.BR.setPower(0.3);
        }

        motors.TR.setPower(0);
        motors.TL.setPower(0);
        motors.BL.setPower(0);
        motors.BR.setPower(0);

        telemetry.addData("Speed" , speed);
        telemetry.update();
    }

}
