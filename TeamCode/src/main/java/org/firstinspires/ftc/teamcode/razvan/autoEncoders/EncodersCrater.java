package org.firstinspires.ftc.teamcode.razvan.autoEncoders;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder-Crater", group = "Encoder")
public class EncodersCrater extends EncodersAuto {

    @Override
    protected void onStart() {
        int dist = 1600;
        int restDist = 0;

        moveRight(1250);

        if(hitGoldIfDetected()) {
            restDist = 1600 - dist;
            moveForward(dist + restDist + 100);
            StopCam();
        }
        else {
            moveForward(-800);
            if(hitGoldIfDetected()) {
                restDist = dist - 800;
                moveForward(dist + restDist + 100);
                StopCam();
            }
            else {
                moveForward(dist);
                if(hitGoldIfDetected()) {
                    restDist = 800 - dist;
                    moveForward(dist + restDist + 100);
                    StopCam();
                }
                else {
                    moveForward(dist + restDist + 100);
                    StopCam();
                }
            }
        }

        rotateRight(-580);
        moveForward(2800);

        // Servo
        addTelemetryWithUpdate("Servo", "Moving Forward");
        markerServo.setPosition(1);
        sleep(2500);

        addTelemetryWithUpdate("Servo", "Moving Backwards");
        markerServo.setPosition(0);

        moveForward(-2000);
        rotateRight(2160);
        moveRight(-350);
        moveForward(2000);

        addTelemetryWithUpdate("Status", "Path Completed");
    }
}
