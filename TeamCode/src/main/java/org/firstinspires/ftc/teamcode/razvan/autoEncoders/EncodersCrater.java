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

        /*iGoldDetectorManager.Pos goldPos = detectorManager.getLastGoldPosition();
        detectorManager.stopDetector();

        f (goldPos == GoldDetectorManager.Pos.LEFT)
        else if (goldPos == GoldDetectorManager.Pos.RIGHT)*/

         moveRight(1200);

         moveForward(-800);
         moveForward(400);moveForward(-400);

         moveForward(800);
         rotate(1080); // 1080 = 90 degrees
    }
}
