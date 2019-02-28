package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motors.WheelMotors;

@TeleOp(name = "Final: TeleOp", group = "Good")
public class FinalTeleOp extends OpMode {

    private static final double PRECISION_MODE_MULTIPLIER = 0.55;
    private static final double MOTOR_SPEED_MULTIPLIER = 0.9;
    private static final double MOTOR_SPEED_ROTATION = 0.3;

    private WheelMotors wheelMotors = null;
    private boolean precisionModeOn;

    @Override
    public void init()
    {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        precisionModeOn = false;
    }

    @Override
    public void loop()
    {
        Movement();
        Strafe();
    }

    public void Movement()
    {
        final double leftX = gamepad1.left_stick_x;
        final double leftY = -gamepad1.left_stick_y;
        double ptl, ptr, pbl, pbr, max;

        ptl = leftY + leftX;
        pbl = leftY + leftX;
        ptr = -leftY + leftX;
        pbr = -leftY + leftX;

        max = ptl;
        if (max < pbl)
            max = pbl;
        else
        if (max < ptr)
            max = ptr;
        else
        if (max < pbr)
            max = pbr;
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
    }

    public void Strafe()
    {
        final double rightX = gamepad1.right_stick_x;

        wheelMotors.TR.setPower(rightX);
        wheelMotors.TL.setPower(rightX);
        wheelMotors.BR.setPower(-rightX);
        wheelMotors.BL.setPower(-rightX);
    }
}
