package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Motors;

public abstract class AutonomousOp extends LinearOpMode {

    protected Motors motors = null;

    @Override
    public void runOpMode() throws InterruptedException {
        motors = new Motors(hardwareMap.dcMotor);
    }

    private void waitForMotors() throws InterruptedException {
        // Wait for the Motor to stop
        while (opModeIsActive()
                && motors.TL.isBusy()
                && motors.TR.isBusy()
                && motors.BL.isBusy()
                && motors.BR.isBusy()) {
            Thread.sleep(50);
        }

        // Stop the Motors
        motors.TL.setPower(0);
        motors.TR.setPower(0);
        motors.BL.setPower(0);
        motors.BR.setPower(0);
    }

    void moveForward(final int position) throws InterruptedException {
        // Reset Counter
        motors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set Motors to run to target position
        motors.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motors.TL.setTargetPosition(position);
        motors.TR.setTargetPosition(-position);
        motors.BL.setTargetPosition(-position);
        motors.BR.setTargetPosition(position);

        // Set Power
        final double direction = Math.atan2(0, 1) - Motors.PI_4;

        final double v1 = Math.cos(direction);
        final double v2 = Math.sin(direction);

        motors.TL.setPower(v1);
        motors.TR.setPower(v2);
        motors.BL.setPower(v2);
        motors.BR.setPower(v1);

        waitForMotors();
    }

    void moveRight(final int position) throws InterruptedException {
        motors.TR.setDirection(DcMotorSimple.Direction.REVERSE);
        motors.BL.setDirection(DcMotorSimple.Direction.REVERSE);
        // Reset Counter
        motors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set Motors to run to target position
        motors.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motors.TL.setTargetPosition(position);
        motors.TR.setTargetPosition(-position);
        motors.BL.setTargetPosition(-position);
        motors.BR.setTargetPosition(position);

        // Set Power
        final double direction = Math.atan2(0, 1) - Motors.PI_4;

        final double v1 = Math.cos(direction);
        final double v2 = Math.sin(direction);

        motors.TL.setPower(v1);
        motors.TR.setPower(v1);
        motors.BL.setPower(v1);
        motors.BR.setPower(v1);

        waitForMotors();
    }

    void rotate45() throws InterruptedException {
        // Reset Counter
        motors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // Set Motors to run to target position
        motors.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motors.TL.setTargetPosition(-10);
        motors.TR.setTargetPosition(-10);
        motors.BL.setTargetPosition(10);
        motors.BR.setTargetPosition(10);

        motors.TL.setPower(-0.5);
        motors.TR.setPower(-0.5);
        motors.BL.setPower(0.5);
        motors.BR.setPower(0.5);

        waitForMotors();
    }
}
