package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto_Depot", group = "Autonomous")
public class AutoDepot extends AutonomousOp {

    @Override
    public void runOpMode() {
        super.runOpMode();

        /*detectorManager.startDetector(hardwareMap);
        addTelemetryWithUpdate("Status", "Looking for Gold");
        detectorManager.detector.setListener(new GoldAlignDetector.GoldAlignListener() {
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

        addTelemetryWithUpdate("Status", "Ready to run");
        waitForStart();

        /*GoldDetectorManager.Pos goldPos = detectorManager.getLastGoldPosition();
        detectorManager.stopDetector();

        if (goldPos == GoldDetectorManager.Pos.LEFT)
        else if (goldPos == GoldDetectorManager.Pos.RIGHT)*/

        addTelemetryWithUpdate("Status", "Forward");
        moveForward(SPEED * 1.1);

        addTelemetryWithUpdate("Servo", "Moving");
        markerServo.setPosition(1);
        sleep(PAUSE * 3);

        addTelemetryWithUpdate("Direction", "Back, Retrieve Servo");
        markerServo.setPosition(0);
        moveForward(SPEED * -0.7);

        addTelemetryWithUpdate("Rotation", "Right");
        rotateLeft(SPEED * -0.75);

        addTelemetryWithUpdate("Direction", "Front");
        moveForward(SPEED * 0.8);

        addTelemetryWithUpdate("Rotation", "Left");
        rotateLeft(SPEED * 0.5);

        addTelemetryWithUpdate("Direction", "Front");
        moveForward(SPEED * 0.85);

        addTelemetryWithUpdate("Status", "Path Completed");
    }
}
