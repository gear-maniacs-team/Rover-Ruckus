package org.firstinspires.ftc.teamcode.autonomous;

import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.GoldDetectorManager;

@Disabled
@Autonomous(name = "AutoBlueDepot", group = "Autonomous")
public class AutoBlueDepot extends AutonomousOp {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        detectorManager.startDetector(hardwareMap);
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
        });

        waitForStart();

        GoldDetectorManager.Pos goldPos = detectorManager.getLastGoldPosition();
        detectorManager.stopDetector();

        moveRight(10);

        if (goldPos == GoldDetectorManager.Pos.LEFT)
            moveForward(10);
        else if (goldPos == GoldDetectorManager.Pos.RIGHT)
            moveForward(-10);
    }
}
