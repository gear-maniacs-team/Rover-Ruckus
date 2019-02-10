package org.firstinspires.ftc.teamcode.razvan.autoEncoders;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder-Crater", group = "Encoder")
public class EncodersCrater extends EncodersAuto {

    @Override
    protected void onStart() {
        DRIVE_POWER = 0.5;

        int dist = 1600;
        int restDist = 0;

        moveRight(1000);
        moveForward(50);

        if(hitGoldIfDetected()) {
            restDist = 1600 - dist;
            moveForward(dist + restDist + 600);
            StopCam();
        }
        else {
            moveForward(-800);
            if(hitGoldIfDetected()) {
                restDist = dist - 800;
                moveForward(dist + restDist + 450);
                StopCam();
            }
            else {
                moveForward(dist);

                moveRight(900);
                sleep(300);

                moveRight(-800);
                sleep(300);

                    restDist = 800 - dist;
                    moveForward(dist + restDist + 450);
                    StopCam();


            }
        }

        rotateRight(-600);
        moveRight(500);
        //moveRight(-300);

        moveForward(2500);

        // Servo
        addTelemetryWithUpdate("Servo", "Moving Forward");
        markerServo.setPosition(1);
        sleep(2300);

        addTelemetryWithUpdate("Servo", "Moving Backwards");
        markerServo.setPosition(0);

        moveForward(-1000);
        //rotateRight(2160);
        //moveRight(600);

        DRIVE_POWER = 0.8;

        moveForward(-3200);

        addTelemetryWithUpdate("Status", "Path Completed");
    }
}
