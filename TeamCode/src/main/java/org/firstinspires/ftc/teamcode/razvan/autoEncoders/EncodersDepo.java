package org.firstinspires.ftc.teamcode.razvan.autoEncoders;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder-Depo", group = "Encoder")
public class EncodersDepo extends EncodersAuto {

    @Override
    protected void onStart() {
        int dist = 1600;
        int restDist = 0;

        moveRight(1250);

        if(hitGoldIfDetected()) {
            restDist = 1600 - dist;
            moveForward(dist + restDist + 600);
            StopCam();
        }
        else {
            moveForward(-800);
            if(hitGoldIfDetected()) {
                restDist = dist - 800;
                moveForward(dist + restDist + 600);
                StopCam();
            }
            else {
                moveForward(dist);

                moveRight(900);
                sleep(300);

                moveRight(-800);
                sleep(300);

                restDist = 800 - dist;
                moveForward(dist + restDist + 600);
                StopCam();


            }
        }

        rotateRight(1600);
        moveRight(-300);
        moveForward(2600);


        // Servo
        addTelemetryWithUpdate("Servo", "Moving Forward");
        markerServo.setPosition(1);
        sleep(2500);

        addTelemetryWithUpdate("Servo", "Moving Backwards");
        markerServo.setPosition(0);

        moveForward(-2000);
        moveRight(400);
        rotateRight(2160);
        moveRight(-600);
        moveForward(2000);

        addTelemetryWithUpdate("Status", "Path Completed");
    }
}
