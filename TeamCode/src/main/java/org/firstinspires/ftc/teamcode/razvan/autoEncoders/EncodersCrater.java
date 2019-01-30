package org.firstinspires.ftc.teamcode.razvan.autoEncoders;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Encoder-Crater", group = "Encoder")
public class EncodersCrater extends EncodersAuto {

    @Override
    public void runOpMode() {
        super.runOpMode();

        /*detectorManager.startDetector(hardwareMap);
        addTelemetryWithUpdate("Status", "Looking for Gold");
        detectorManager.setListener(new GoldAlignDetector.GoldAlignListener() {
            @Override
            public void onAlignChange(boolean found, boolean aligned, double lastXPos, double lastYPos) {
                if (found) {
                    telemetry.addData("Gold Status", "Last X Pos: %d, Last Y Pos: %d",
                            lastXPos, lastYPos);
                } else {
                    telemetry.addData("Gold Status", "No Gold found yet");
                }
                telemetry.update();
            }
        });*/

        waitForStart();

        /*GoldDetectorManager.Pos goldPos = detectorManager.getLastGoldPosition();
        detectorManager.stopDetector();

        if (goldPos == GoldDetectorManager.Pos.LEFT)
        else if (goldPos == GoldDetectorManager.Pos.RIGHT)*/

        moveRight(1500);

        moveForward(-300);
        sleep(500);
        moveForward(900);
        sleep(500);
        moveForward(900);
        sleep(500);

        moveForward(1300);
        rotate(-580);

        moveForward(2800);

        // Servo
        addTelemetryWithUpdate("Servo", "Moving Forward");
        markerServo.setPosition(1);
        sleep(2500);

        addTelemetryWithUpdate("Servo", "Moving Backwards");
        markerServo.setPosition(0);

        moveForward(-2000);
        rotate(2160);
        moveRight(-430);
        moveForward(2000);

        addTelemetryWithUpdate("Status", "Path Completed");
    }
}
