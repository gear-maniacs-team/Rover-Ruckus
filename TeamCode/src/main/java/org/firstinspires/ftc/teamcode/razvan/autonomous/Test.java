package org.firstinspires.ftc.teamcode.razvan.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "MotorTest")
public class Test extends EncodersAuto {

    @Override
    protected void onStart() {
        //stopCamera();
        moveForward(500);
    }
}
