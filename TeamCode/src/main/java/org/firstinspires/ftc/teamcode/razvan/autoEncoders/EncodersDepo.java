package org.firstinspires.ftc.teamcode.razvan.autoEncoders;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder-Depo", group = "Encoder")
public class EncodersDepo extends EncodersAuto {

    @Override
    public void runOpMode() {
        super.runOpMode();

        waitForStart();

        moveRight(1500);

        moveForward(-300);
        sleep(500);
        moveForward(900);
        sleep(500);
        moveForward(900);
        sleep(500);

        moveForward(1300);
        rotate(1580);
        moveRight(150);

        moveForward(2400);

        // Servo
        addTelemetryWithUpdate("Servo", "Moving Forward");
        markerServo.setPosition(1);
        sleep(2500);

        addTelemetryWithUpdate("Servo", "Moving Backwards");
        markerServo.setPosition(0);


        moveForward(-2400);
        moveRight(-400);
        rotate(2160);
        moveRight(400);
        moveForward(2000);

        addTelemetryWithUpdate("Status", "Path Completed");
    }
}
