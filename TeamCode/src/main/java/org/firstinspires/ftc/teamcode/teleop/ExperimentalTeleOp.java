package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.motors.ArmMotors;
import org.firstinspires.ftc.teamcode.motors.WheelMotors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TeleOp(name = "Experimental", group = "Test")
public class ExperimentalTeleOp extends OpMode {

    private static final double PRECISION_MODE_MULTIPLIER = 0.45;
    private static final double MOTOR_SPEED_MULTIPLIER = 0.8;
    private static final double MOTOR_SPEED_STRAFE = 0.6;
    private static final double ARM_EXTENSION_SPEED_MULTIPLIER = 0.75;
    private static final double ARM_ANGLE_SPEED_MULTIPLIER = 0.55;
    private static final double COLLECTOR_SPEED = 0.5;
    private static final double LATCH_SPEED = 1;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private volatile boolean isRunning = false;
    private WheelMotors wheelMotors = null;
    private ArmMotors armMotors = null;

    @Override
    public void init() {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        armMotors = new ArmMotors(hardwareMap.dcMotor);

        wheelMotors.setModeAll(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotors.latchMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void start() {
        isRunning = true;
        Runnable armRunnable = () -> {
            while (isRunning) {
                armMovement();
                collector();
            }
        };
        executor.execute(armRunnable);
    }

    @Override
    public void loop() {
        movement();
        latching();
        telemetry.update();
    }

    @Override
    public void stop() {
        isRunning = false;
    }

    private void movement() {
        final double leftX = gamepad1.left_stick_x;
        final double leftY = -gamepad1.left_stick_y;

        double pbl = leftY + leftX;
        double ptl = leftY + leftX;
        double ptr = -leftY + leftX;
        double pbr = -leftY + leftX;

        double max = ptl;
        if (max < pbl)
            max = pbl;
        else if (max < ptr)
            max = ptr;
        else if (max < pbr)
            max = pbr;

        if (max > 1) {
            ptl /= max;
            ptr /= max;
            pbl /= max;
            pbr /= max;
        }

        final boolean precisionModeOn = gamepad1.a;

        if (precisionModeOn) {
            ptr *= PRECISION_MODE_MULTIPLIER;
            ptl *= PRECISION_MODE_MULTIPLIER;
            pbr *= PRECISION_MODE_MULTIPLIER;
            pbl *= PRECISION_MODE_MULTIPLIER;
        }

        ptr *= MOTOR_SPEED_MULTIPLIER;
        ptl *= MOTOR_SPEED_MULTIPLIER;
        pbr *= MOTOR_SPEED_MULTIPLIER;
        pbl *= MOTOR_SPEED_MULTIPLIER;

        if (gamepad1.right_stick_x > 0) {
            // Right Strafe
            wheelMotors.TR.setPower(ptr + MOTOR_SPEED_STRAFE);
            wheelMotors.TL.setPower(ptl + MOTOR_SPEED_STRAFE);
            wheelMotors.BR.setPower(pbr - MOTOR_SPEED_STRAFE);
            wheelMotors.BL.setPower(pbl - MOTOR_SPEED_STRAFE);
        } else if (gamepad1.right_stick_x < 0) {
            // Left Strafe
            wheelMotors.TR.setPower(ptr - MOTOR_SPEED_STRAFE);
            wheelMotors.TL.setPower(ptl - MOTOR_SPEED_STRAFE);
            wheelMotors.BR.setPower(pbr + MOTOR_SPEED_STRAFE);
            wheelMotors.BL.setPower(pbl + MOTOR_SPEED_STRAFE);
        } else {
            wheelMotors.TR.setPower(ptr);
            wheelMotors.TL.setPower(ptl);
            wheelMotors.BR.setPower(pbr);
            wheelMotors.BL.setPower(pbl);
        }

        telemetry.addData("Precision Mode On", "%b\n", precisionModeOn);
    }

    private void latching() {
        double latchingPower = 0;

        if (gamepad1.dpad_up)
            latchingPower = LATCH_SPEED;
        else if (gamepad1.dpad_down)
            latchingPower = -LATCH_SPEED;

        armMotors.latchMotor.setPower(latchingPower);

        telemetry.addData("Latching Power", latchingPower);
    }

    private void armMovement() {
        final double armAnglePower = -gamepad2.left_stick_y * ARM_ANGLE_SPEED_MULTIPLIER;
        armMotors.armAngle.setPower(armAnglePower);

        final double armExtensionPower = gamepad2.right_stick_y * ARM_EXTENSION_SPEED_MULTIPLIER;
        armMotors.armExtension.setPower(armExtensionPower);

        //telemetry.addData("Arm Angle Power", armAnglePower);
        //telemetry.addData("Arm Extension Power", armExtensionPower);
    }

    private void collector() {
        double collectorPower = 0;

        if (gamepad2.right_bumper)
            collectorPower = COLLECTOR_SPEED;
        else if (gamepad2.left_bumper)
            collectorPower = -COLLECTOR_SPEED;
        armMotors.collector.setPower(collectorPower);

        //telemetry.addData("Collector Power", collectorPower);
    }
}
