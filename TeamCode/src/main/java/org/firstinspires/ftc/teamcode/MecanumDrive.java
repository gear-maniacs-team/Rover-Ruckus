package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motors.ArmMotors;
import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@TeleOp(name = "The Good One", group = "Good")
public class MecanumDrive extends OpMode {

    private static final double PRECISION_MODE_MULTIPLIER = 0.55;
    private static final double MOTOR_SPEED_MULTIPLIER = 0.9;
    private static final double MOTOR_SPEED_ROTATION = 0.3;
    private static final double ARM_SPEED_MULTIPLIER = 0.4;
    private static final double COLLECTOR_SPEED = 0.3;
    private static final double LATCH_SPEED = 1;

    private WheelMotors wheelMotors = null;
    private ArmMotors armMotors = null;
    private boolean precisionModeOn;

    @Override
    public void init()
    {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        armMotors = new ArmMotors(hardwareMap.dcMotor);
        precisionModeOn = false;
    }

    @Override
    public void loop()
    {
        final double leftX = gamepad1.left_stick_x;
        final double leftY = -gamepad1.left_stick_y;

        final double wheelsSpeed = Math.hypot(leftY, leftX);
        final double direction = Math.atan2(leftX, leftY) - WheelMotors.PI_4;

        double speed1 = wheelsSpeed * Math.cos(direction) * MOTOR_SPEED_MULTIPLIER;
        double speed2 = wheelsSpeed * Math.sin(direction) * MOTOR_SPEED_MULTIPLIER;

        if (gamepad1.x) {
            precisionModeOn = !precisionModeOn;
            sleep(200);
        }

        if (precisionModeOn) {
            speed1 *= PRECISION_MODE_MULTIPLIER;
            speed2 *= PRECISION_MODE_MULTIPLIER;
        }

        wheelMotors.TL.setPower(speed1);
        wheelMotors.TR.setPower(speed2);
        wheelMotors.BL.setPower(-speed2);
        wheelMotors.BR.setPower(-speed1);

        // TODO Combine rotational movement with forwards, backwards movement

        while (gamepad1.right_stick_x > 0)
            wheelMotors.setPowerAll(MOTOR_SPEED_ROTATION);

        while (gamepad1.right_stick_x < 0)
            wheelMotors.setPowerAll(-MOTOR_SPEED_ROTATION);

        wheelMotors.setPowerAll(0);

        // Latching
        {
            double latchSpeed = 0;
            if (gamepad1.dpad_up)
                latchSpeed = -LATCH_SPEED;
            else if (gamepad1.dpad_down)
                latchSpeed = LATCH_SPEED;
            armMotors.latchMotor.setPower(latchSpeed);
        }

        // Collector
        {
            double collectorPower = 0;
            if (gamepad2.right_bumper)
                collectorPower = COLLECTOR_SPEED;
            else if (gamepad2.left_bumper)
                collectorPower = -COLLECTOR_SPEED;
            armMotors.collector.setPower(collectorPower);
        }

        // Arm
        final double armAngleSpeed = -gamepad2.left_stick_y * ARM_SPEED_MULTIPLIER;
        armMotors.armAngle.setPower(armAngleSpeed);

        final double armExtensionSpeed = -gamepad2.right_stick_y * ARM_SPEED_MULTIPLIER;
        armMotors.armExtension.setPower(armExtensionSpeed);

        telemetry.addData("Wheels Speed", wheelsSpeed);
        telemetry.addData("Arm Angle Speed", armAngleSpeed);
        telemetry.addData("Arm Extension Speed", armExtensionSpeed);
        telemetry.addData("Precision Mode On", precisionModeOn);
        telemetry.update();
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
