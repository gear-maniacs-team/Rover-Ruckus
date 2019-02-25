package org.firstinspires.ftc.teamcode.razvan.autoEncoders;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder-Latching", group = "Encoder")
public class Latching extends EncodersAuto {

    @Override
    protected void onStart() {
        stopCamera();
        lowerRobot();
    }
}
