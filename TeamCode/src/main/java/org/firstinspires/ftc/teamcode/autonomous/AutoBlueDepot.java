package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.GoldPos;

@Autonomous(name = "AutoBlueDepot", group = "Autonomous")
public class AutoBlueDepot extends AutonomousOp {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        startDetector();
        waitForStart();

        GoldPos goldPos = GoldPos.MIDDLE;
        int y = (int)detector.getYPosition(); // from 0 to 480

        if (y > 0 && y <= 160)
            goldPos = GoldPos.LEFT;
        else if (y >= 320)
            goldPos = GoldPos.RIGHT;
        else {
            telemetry.addData("Error 404", "Gold not found");
            telemetry.update();
        }

        stopDetector();

        moveRight(10);

        if (goldPos == GoldPos.LEFT)
            moveForward(20);
        else if (goldPos == GoldPos.RIGHT)
            moveForward(-20);
    }
}
