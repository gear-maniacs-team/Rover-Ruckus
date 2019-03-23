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
        moveRight(-390);

        moveForward(-2200);

        deployMarker();

        moveForward(2000, 0.7);
        moveRight(-140, 0.4);
        moveForward(500);

        lowerArm();
    }
}
