package org.firstinspires.ftc.teamcode.razvan.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder-Crater", group = "Encoder")
public class EncodersCrater extends EncodersAuto {

    @Override
    protected void onStart() {
        int dist = 1600;
        int restDist;

        lowerRobot();

        moveForward(300);
        moveRight(1250);
        moveForward(-170);

        if (hitGoldIfDetected()) {
            restDist = 1600 - dist;
            moveForward(dist + restDist + 600);
            stopCamera();
        } else {
            moveForward(-800);
            if (hitGoldIfDetected()) {
                restDist = dist - 800;
                moveForward(dist + restDist + 450);
                stopCamera();
            } else {
                moveForward(dist);

                moveRight(900);
                sleep(300);

                moveRight(-800);
                sleep(300);

                restDist = 800 - dist;
                moveForward(dist + restDist + 500);
                stopCamera();
            }
        }

        rotateRight(1600);
        moveRight(-800, 0.2);
        moveRight(280);

        moveForward(-2500);

        deployMarker();

        moveForward(3300);

        lowerArm();
    }
}
