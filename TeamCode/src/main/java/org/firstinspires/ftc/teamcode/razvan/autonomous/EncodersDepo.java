package org.firstinspires.ftc.teamcode.razvan.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder-Depo", group = "Encoder")
public class EncodersDepo extends EncodersAuto {

    @Override
    protected void onStart() {
        lowerRobot();

        moveForward(300);
        moveRight(900);
        moveForward(-170);

        sampling();

        /*rotateRight(-540);
        moveRight(800,0.2);
        moveRight(-280);
        moveForward(-1500);

        deployMarker();

        moveForward(2600);

        //lowerArm();*/
    }

    private void sampling() {
        int dist = 1600;
        int restDist;

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

                moveRight(500);
                sleep(300);

                moveRight(-400);
                sleep(300);

                restDist = 800 - dist;
                moveForward(dist + restDist + 500);
                stopCamera();
            }
        }
    }
}
