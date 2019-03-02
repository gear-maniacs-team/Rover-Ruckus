package org.firstinspires.ftc.teamcode.razvan.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder-Depo", group = "Encoder")
public class EncodersDepo extends EncodersAuto {

    @Override
    protected void onStart() {
        int dist = 1600;
        int restDist;

        lowerRobot();

        moveForward(300);
        moveRight(1000);
        moveForward(-300);

        moveRight(1150);

        if (hitGoldIfDetected()) {
            restDist = 1600 - dist;
            moveForward(dist + restDist + 600);
            stopCamera();
        } else {
            moveForward(-800);
            if (hitGoldIfDetected()) {
                restDist = dist - 800;
                moveForward(dist + restDist + 600);
                stopCamera();
            } else {
                moveForward(dist);

                moveRight(900);
                sleep(300);

                moveRight(-800);
                sleep(300);

                restDist = 800 - dist;
                moveForward(dist + restDist + 600);
                stopCamera();
            }
        }

        rotateRight(1600);
        moveRight(-700);
        moveRight(200);
        moveForward(2600);

        deployMarker();

        /*
        // Servo
        addTelemetryWithUpdate("Servo", "Moving Forward");
        markerServo.setPosition(1);
        sleep(2500);

        addTelemetryWithUpdate("Servo", "Moving Backwards");
        markerServo.setPosition(0);
        */

        moveForward(-1200);
        moveRight(400);
        //rotateRight(2160);
        moveRight(600);
        moveForward(-3300);
    }
}
