package org.firstinspires.ftc.teamcode.razvan.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder-Depo", group = "Encoder")
public class EncodersDepo extends EncodersAuto {

    @Override
    protected void onStart() {
        int dist = 1600;
        int restDist;

        //lowerRobot();

        moveForward(300);
        moveRight(1300);
        moveForward(-200);

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

        rotateRight(-540);
        moveRight(700,0.2);
        moveRight(-200);
        moveForward(-1500);

        //deployMarker();

        /*
        // Servo
        addTelemetryWithUpdate("Servo", "Moving Forward");
        markerServo.setPosition(1);
        sleep(2500);

        addTelemetryWithUpdate("Servo", "Moving Backwards");
        markerServo.setPosition(0);
        */

        moveForward(2600);

    }
}
