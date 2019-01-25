package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@TeleOp(name = "The Good One")
public class MecanumDrive extends OpMode {

    private WheelMotors wheelMotors = null;
    //private ArmMotors armMotors = null;
    private double armSpeed = 1;
    private double collectorSpeed = 0.4;
    private boolean breaker = true;

    @Override
    public void init()
    {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        //armMotors = new ArmMotors(hardwareMap.dcMotor);
    }

    @Override
    public void loop()
    {
        double leftX = gamepad1.left_stick_x;
        double leftY = -gamepad1.left_stick_y;

        double speed = Math.hypot(leftY, leftX);
        double direction = Math.atan2(leftX, leftY) - WheelMotors.PI_4;

        final double v1 = speed * Math.cos(direction);
        final double v2 = speed * Math.sin(direction);

        double speed1 = v1*0.75;
        double speed2 = v2*0.75;

        wheelMotors.TL.setPower(speed1);
        wheelMotors.TR.setPower(speed2);
        wheelMotors.BL.setPower(-speed2);
        wheelMotors.BR.setPower(-speed1);

        while (gamepad1.right_stick_x > 0)
        {
            wheelMotors.TR.setPower(0.3);
            wheelMotors.TL.setPower(0.3);
            wheelMotors.BL.setPower(0.3);
            wheelMotors.BR.setPower(0.3);
        }
        while (gamepad1.right_stick_x < 0)
        {
            wheelMotors.TR.setPower(-0.3);
            wheelMotors.TL.setPower(-0.3);
            wheelMotors.BL.setPower(-0.3);
            wheelMotors.BR.setPower(-0.3);
        }

        wheelMotors.setPowerAll(0);
        /*
        // Arms
        if (gamepad1.dpad_up)
            armMotors.extender.setPower(armSpeed);
        if (gamepad1.dpad_down)
            armMotors.extender.setPower(-armSpeed);
        armMotors.extender.setPower(0);

        if (gamepad1.a)
            armMotors.angle.setPower(armSpeed);
        if (gamepad1.y)
            armMotors.angle.setPower(-armSpeed);
        armMotors.angle.setPower(0);

        if(gamepad1.b && breaker)
        {
            armMotors.collector.setPower(collectorSpeed);
            breaker = false;
        }
        else
            if(gamepad1.b && !breaker)
            {
                armMotors.collector.setPower(0);
                breaker = true;
            }
        */
        telemetry.addData("Wheels Speed", speed);
        //telemetry.addData("Arm Extender Speed", armSpeed);
        telemetry.update();
    }

}
