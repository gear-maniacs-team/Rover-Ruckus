package org.firstinspires.ftc.teamcode.autonomous;

import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.GoldPos;

@Autonomous(name = "AutoBlueDepot", group = "Autonomous")
public class AutoBlueDepot extends AutonomousOp {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        startDetector();

        detector.setListener(new GoldAlignDetector.GoldAlignListener() {
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

        GoldPos goldPos = GoldPos.MIDDLE;
        double y = detector.getYPosition(); // from 0 to 480

        if (y > 0 && y < 160)
            goldPos = GoldPos.LEFT;
        else if (y > 320)
            goldPos = GoldPos.RIGHT;
        else if (y <= 0)
            telemetry.addData("Error 404", "Gold not found");
        telemetry.update();

        stopDetector();

        moveRight(10);

        if (goldPos == GoldPos.LEFT)
            moveForward(10);
        else if (goldPos == GoldPos.RIGHT)
            moveForward(-10);
    }
}
