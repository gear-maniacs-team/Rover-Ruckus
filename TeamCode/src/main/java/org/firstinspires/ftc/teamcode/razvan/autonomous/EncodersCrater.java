package org.firstinspires.ftc.teamcode.razvan.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Crater", group = "Encoder")
public class EncodersCrater extends EncodersAuto {

    @Override
    protected void onStart() {
        lowerRobot();
        waitForDetector();
        movementWithSampling();

        rotateRight(1600);
        moveRight(-750, 0.25);
        moveRight(100);

        moveForward(-2500, 0.7);

        deployMarker();

        moveForward(3350, 0.7);

        lowerArm();
    }
}
