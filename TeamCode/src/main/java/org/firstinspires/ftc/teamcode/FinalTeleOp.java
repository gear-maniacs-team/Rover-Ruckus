package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motors.ArmMotors;
import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@TeleOp(name = "Final: TeleOp", group = "Good")
public class FinalTeleOp extends OpMode {

    private static final double PRECISION_MODE_MULTIPLIER = 0.5;
    private static final double MOTOR_SPEED_MULTIPLIER = 0.9;
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
    }

    @Override
    public void loop()
    {
        Movement();
        //Strafe();
        Latching();
        ArmMovement();
        Collector();
    }

    private void Movement()
    {
        final double leftX = gamepad1.left_stick_x;
        final double leftY = -gamepad1.left_stick_y;
        final double rightX = gamepad1.right_stick_x;
        double ptl, ptr, pbl, pbr, max;

        ptl = leftY + leftX + rightX;
        pbl = leftY + leftX - rightX;
        ptr = -leftY + leftX + rightX;
        pbr = -leftY + leftX - rightX;

        max = ptl;
        if (max < pbl) max = pbl;
        else if (max < ptr) max = ptr;
            else if (max < pbr) max = pbr;

        if (max > 1)
        {
            ptl /= max;
            ptr /= max;
            pbl /= max;
            pbr /= max;
        }

        wheelMotors.TR.setPower(ptr);
        wheelMotors.TL.setPower(ptl);
        wheelMotors.BR.setPower(pbr);
        wheelMotors.BL.setPower(pbl);

        if(gamepad1.a) {
            wheelMotors.TR.setPower(ptr * PRECISION_MODE_MULTIPLIER);
            wheelMotors.TL.setPower(ptl * PRECISION_MODE_MULTIPLIER);
            wheelMotors.BR.setPower(pbr * PRECISION_MODE_MULTIPLIER);
            wheelMotors.BL.setPower(pbl * PRECISION_MODE_MULTIPLIER);
            precisionModeOn = !precisionModeOn;
        }

        telemetry.addData("Precision Mode On", precisionModeOn);
        telemetry.update();
    }

    private void Strafe()
    {
        final double rightX = gamepad1.right_stick_x;

        wheelMotors.TR.setPower(rightX);
        wheelMotors.TL.setPower(rightX);
        wheelMotors.BR.setPower(-rightX);
        wheelMotors.BL.setPower(-rightX);
    }

    private void Latching()
    {
        double latchSpeed = 0;
        if (gamepad1.dpad_up)
            latchSpeed = -LATCH_SPEED;
        else if (gamepad1.dpad_down)
            latchSpeed = LATCH_SPEED;
        armMotors.latchMotor.setPower(latchSpeed);
    }

    private void ArmMovement()
    {
        double armAngleSpeed = -gamepad2.left_stick_y * ARM_SPEED_MULTIPLIER;
        armMotors.armAngle.setPower(armAngleSpeed);

        double armExtensionSpeed = -gamepad2.right_stick_y * ARM_SPEED_MULTIPLIER;
        armMotors.armExtension.setPower(armExtensionSpeed);

        telemetry.addData("Arm Angle Speed", armAngleSpeed);
        telemetry.addData("Arm Extension Speed", armExtensionSpeed);
        telemetry.update();
    }

    private void Collector()
    {
        double collectorPower = 0;
        if (gamepad2.right_bumper)
            collectorPower = COLLECTOR_SPEED;
        else if (gamepad2.left_bumper)
            collectorPower = -COLLECTOR_SPEED;
        armMotors.collector.setPower(collectorPower);
    }
}
