package org.firstinspires.ftc.teamcode.razvan.autoEncoders;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder-Crater", group = "Encoder")
public class EncodersCrater extends EncodersAuto {

    @Override
    protected void onStart() {
        moveRight(1250);

        moveForward(-1100);
        hitGoldIfDetected();
        moveForward(800);
        hitGoldIfDetected();
        moveForward(800);
        hitGoldIfDetected();

        // StopCam();

        moveForward(1600);
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
