package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motors.ArmMotors;
import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@TeleOp(name = "The Good One", group = "Good")
public class MecanumDrive extends OpMode {

    private WheelMotors wheelMotors = null;
    private ArmMotors armMotors = null;
    private double latchSpeed = 1;
    //private double armSpeed = 1;
    //private double collectorSpeed = 0.4;

    @Override
    public void init()
    {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        armMotors = new ArmMotors(hardwareMap.dcMotor);
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

        double speed1 = v1*0.9;
        double speed2 = v2*0.9;

        wheelMotors.TL.setPower(speed1);
        wheelMotors.TR.setPower(speed2);
        wheelMotors.BL.setPower(-speed2);
        wheelMotors.BR.setPower(-speed1);

        //TODO Combine rotational movement with forwards, backwards movement

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

        wheelMotors.TR.setPower(0);
        wheelMotors.TL.setPower(0);
        wheelMotors.BL.setPower(0);
        wheelMotors.BR.setPower(0);

        // Arms

        //Latching
        if (gamepad1.dpad_up)
            armMotors.latchMotor.setPower(-latchSpeed);
        if (gamepad1.dpad_down)
            armMotors.latchMotor.setPower(latchSpeed);
        armMotors.latchMotor.setPower(0);

        telemetry.addData("Wheels Speed", speed);
        //telemetry.addData("Arm Extender Speed", armSpeed);
        telemetry.update();
    }

}
