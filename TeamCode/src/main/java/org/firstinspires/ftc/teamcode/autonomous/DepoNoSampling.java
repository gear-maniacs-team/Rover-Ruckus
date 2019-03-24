package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Depo NO Sampling", group = "Encoder")
public class DepoNoSampling extends EncodersAuto {

    @Override
    protected boolean isVuforiaEnabled() {
        return false;
    }

    @Override
    protected void onStart() {
        lowerRobot();

        moveForward(-200);
        moveRight(1200, 0.5);
        moveForward(2400);

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
