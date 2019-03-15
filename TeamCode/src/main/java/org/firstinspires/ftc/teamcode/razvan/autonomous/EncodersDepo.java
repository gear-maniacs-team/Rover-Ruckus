package org.firstinspires.ftc.teamcode.razvan.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder-Depo", group = "Encoder")
public class EncodersDepo extends EncodersAuto {

    @Override
    protected void onStart() {
        lowerRobot();
        movementWithSampling();

        rotateRight(-540);
        moveRight(800, 0.2);
        moveRight(-280);

        moveForward(-1900);

        deployMarker();

        moveForward(3400);

        lowerArm();
    }
}
