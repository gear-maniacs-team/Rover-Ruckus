package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motors.ArmMotors;
import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@TeleOp(name = "Mecanum")
public class MecanumDrive extends OpMode {

    private WheelMotors wheelMotors = null;
    private ArmMotors armMotors = null;

    @Override
    public void init() {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        armMotors = new ArmMotors(hardwareMap.dcMotor);
    }

    @Override
    public void loop() {
        double leftX = gamepad1.left_stick_x;
        double leftY = -gamepad1.left_stick_y;

        double speed = Math.hypot(leftY, leftX);
        double direction = Math.atan2(leftX, leftY) - WheelMotors.PI_4;

        double v1 = speed * Math.cos(direction);
        double v2 = speed * Math.sin(direction);

        wheelMotors.TL.setPower(v1 * 0.75);
        wheelMotors.TR.setPower(v2 * 0.75);
        wheelMotors.BL.setPower(v2 * 0.75);
        wheelMotors.BR.setPower(v1 * 0.75);

        while (gamepad1.right_stick_x > 0) {
            wheelMotors.TR.setPower(0.3);
            wheelMotors.TL.setPower(0.3);
            wheelMotors.BL.setPower(-0.3);
            wheelMotors.BR.setPower(-0.3);
        }
        while (gamepad1.right_stick_x < 0) {
            wheelMotors.TR.setPower(-0.3);
            wheelMotors.TL.setPower(-0.3);
            wheelMotors.BL.setPower(0.3);
            wheelMotors.BR.setPower(0.3);
        }

        wheelMotors.TR.setPower(0);
        wheelMotors.TL.setPower(0);
        wheelMotors.BL.setPower(0);
        wheelMotors.BR.setPower(0);

        // Arms
        while (gamepad1.dpad_up)
            armMotors.extender.setPower(0.2);
        while (gamepad1.dpad_down)
            armMotors.extender.setPower(-0.2);
        armMotors.extender.setPower(0);

        telemetry.addData("Wheels Speed", speed);
        telemetry.update();
    }

}
