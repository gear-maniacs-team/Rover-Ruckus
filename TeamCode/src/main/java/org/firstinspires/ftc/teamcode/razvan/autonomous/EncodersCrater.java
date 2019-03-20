package org.firstinspires.ftc.teamcode.razvan.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder-Crater", group = "Encoder")
public class EncodersCrater extends EncodersAuto {

    @Override
    protected void onStart() {
        lowerRobot();
        waitForDetector();
        movementWithSampling();

        rotateRight(1600);
        moveRight(-800, 0.2);
        moveRight(280);

        moveForward(-2500);

        deployMarker();

        moveForward(3400);

        lowerArm();
    }
}
