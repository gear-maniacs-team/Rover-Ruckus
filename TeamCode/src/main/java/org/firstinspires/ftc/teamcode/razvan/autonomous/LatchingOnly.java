package org.firstinspires.ftc.teamcode.razvan.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "LatchingOnly", group = "Test")
public class LatchingOnly extends EncodersAuto {

    @Override
    protected void onStart() {
        stopCamera();
        lowerRobot();
        //moveForward(300);
        //moveRight(300);
    }
}
