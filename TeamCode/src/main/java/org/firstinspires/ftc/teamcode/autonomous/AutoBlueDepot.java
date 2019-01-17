package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AutoBlueDepot", group = "Autonomous")
public class AutoBlueDepot extends AutonomousOp {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        waitForStart();

        moveForward(8);
        moveRight(8);
        rotate45();

    }
}
