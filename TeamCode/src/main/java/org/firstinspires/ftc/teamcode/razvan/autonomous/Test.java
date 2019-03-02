package org.firstinspires.ftc.teamcode.razvan.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "ServoTest")
public class ArmTest extends EncodersAuto {


    @Override
    protected void onStart() {

        stopCamera();
        //markerServo.setPosition(0);
        //sleep(2300);
        deployMarker();
    }
}
