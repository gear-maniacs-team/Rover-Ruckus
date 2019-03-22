package org.firstinspires.ftc.teamcode.razvan.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Depo", group = "Encoder")
public class EncodersDepo extends EncodersAuto {

    @Override
    protected void onStart() {
        lowerRobot();
        waitForDetector();
        movementWithSampling();

        rotateRight(-540);
        moveRight(750, 0.25);
        moveRight(-380);

        moveForward(-2200, 0.7);

        deployMarker();

        moveForward(2500);

        lowerArm();
    }
}
