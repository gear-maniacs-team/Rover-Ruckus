package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.motors.ArmMotors;
import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@TeleOp(name = "Final: TeleOp", group = "Good")
public class FinalTeleOp extends OpMode {

    private static final double PRECISION_MODE_MULTIPLIER = 0.3;
    private static final double MOTOR_SPEED_MULTIPLIER = 0.9;
    private static final double MOTOR_SPEED_STRAFE = 0.6;
    private static final double ARM_SPEED_MULTIPLIER = 0.4;
    private static final double COLLECTOR_SPEED = 0.3;
    private static final double LATCH_SPEED = 1;

    private WheelMotors wheelMotors = null;
    private ArmMotors armMotors = null;
    private TouchSensor latchSense = null;
    private boolean precisionModeOn;

    @Override
    public void init()
    {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        armMotors = new ArmMotors(hardwareMap.dcMotor);
        latchSense = hardwareMap.touchSensor.get("LatchSensor");
        precisionModeOn = false;
    }

    @Override
    public void loop()
    {
        Movement();
        Latching();
        ArmMovement();
        Collector();

        telemetry.update();
    }

    private void Movement()
    {
        final double leftX = gamepad1.left_stick_x;
        final double leftY = -gamepad1.left_stick_y;

        double pbl = leftY + leftX;
        double ptl = leftY + leftX;
        double ptr = -leftY + leftX;
        double pbr = -leftY + leftX;

        double max = ptl;
        if (max < pbl) max = pbl;
        else if (max < ptr) max = ptr;
        else if (max < pbr) max = pbr;

        if (max > 1) {
            ptl /= max;
            ptr /= max;
            pbl /= max;
            pbr /= max;
        }

        // Strafe Right
        while (gamepad1.right_stick_x > 0) {
            wheelMotors.TR.setPower(MOTOR_SPEED_STRAFE);
            wheelMotors.TL.setPower(MOTOR_SPEED_STRAFE);
            wheelMotors.BR.setPower(-MOTOR_SPEED_STRAFE);
            wheelMotors.BL.setPower(-MOTOR_SPEED_STRAFE);
        }

        // Strafe Left
        while (gamepad1.right_stick_x < 0) {
            wheelMotors.TR.setPower(-MOTOR_SPEED_STRAFE);
            wheelMotors.TL.setPower(-MOTOR_SPEED_STRAFE);
            wheelMotors.BR.setPower(MOTOR_SPEED_STRAFE);
            wheelMotors.BL.setPower(MOTOR_SPEED_STRAFE);
        }

        if (gamepad1.a) {
            precisionModeOn = !precisionModeOn;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (precisionModeOn) {
            ptr *= PRECISION_MODE_MULTIPLIER;
            ptl *= PRECISION_MODE_MULTIPLIER;
            pbr *= PRECISION_MODE_MULTIPLIER;
            pbl *= PRECISION_MODE_MULTIPLIER;
        }

        wheelMotors.TR.setPower(ptr * MOTOR_SPEED_MULTIPLIER);
        wheelMotors.TL.setPower(ptl * MOTOR_SPEED_MULTIPLIER);
        wheelMotors.BR.setPower(pbr * MOTOR_SPEED_MULTIPLIER);
        wheelMotors.BL.setPower(pbl * MOTOR_SPEED_MULTIPLIER);

        telemetry.addData("Precision Mode On", "%b\n", precisionModeOn);
    }

    private void Latching()
    {
        double latchingPower = 0;

        if (gamepad1.dpad_up)
            latchingPower = LATCH_SPEED;
        else if (gamepad1.dpad_down)
            latchingPower = -LATCH_SPEED;

        if(latchSense.getValue() != 0 && gamepad1.dpad_down) {
            latchingPower = 0;
            telemetry.addData("Impossible", "to go down");
        }
        armMotors.latchMotor.setPower(latchingPower);

        telemetry.addData("Latching Power", latchingPower);
    }

    private void ArmMovement()
    {
        final double armAnglePower = gamepad2.left_stick_y * ARM_SPEED_MULTIPLIER;
        armMotors.armAngle.setPower(armAnglePower);

        final double armExtensionPower = -gamepad2.right_stick_y * ARM_SPEED_MULTIPLIER;
        armMotors.armExtension.setPower(armExtensionPower);

        telemetry.addData("Arm Angle Power", armAnglePower);
        telemetry.addData("Arm Extension Power", armExtensionPower);
    }

    private void Collector()
    {
        double collectorPower = 0;
        if (gamepad2.right_bumper)
            collectorPower = COLLECTOR_SPEED;
        else if (gamepad2.left_bumper)
            collectorPower = -COLLECTOR_SPEED;
        armMotors.collector.setPower(collectorPower);

        telemetry.addData("Collector Power", collectorPower);
    }
}
