package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.opencv.android.OpenCVLoader;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {

    private DcMotor motorLeft = null;
    private DcMotor motorRight = null;

    @Override
    public void init() {
        OpenCVLoader.initDebug();

        motorLeft = hardwareMap.dcMotor.get("hex40first");
        motorRight = hardwareMap.dcMotor.get("hex40second");
    }

    @Override
    public void loop() {
        // throttle:  left_stick_y ranges from -1 to 1, where -1 is full up,  and 1 is full down
        // direction: left_stick_x ranges from -1 to 1, where -1 is full left and 1 is full right
        float throttle = -gamepad1.left_stick_y;
        float direction = gamepad1.left_stick_x;

        //tank drive calculation
        float right = throttle - direction;
        float left = throttle + direction;

        //set the motor calculation
        motorRight.setPower(right);
        motorLeft.setPower(left);
    }

    @Override
    public void stop() {
        // no action needed
    }
}
