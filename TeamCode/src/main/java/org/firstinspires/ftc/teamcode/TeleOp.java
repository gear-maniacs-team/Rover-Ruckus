package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.opencv.android.OpenCVLoader;

public class TeleOp extends OpMode {

    private DcMotor motorLeft = null;
    private DcMotor motorRight = null;

    @Override
    public void init() {
        OpenCVLoader.initDebug();

        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");
    }

    @Override
    public void loop() {

    }
}
