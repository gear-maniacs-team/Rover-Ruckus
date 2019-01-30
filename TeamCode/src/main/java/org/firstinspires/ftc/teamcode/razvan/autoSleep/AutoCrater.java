package org.firstinspires.ftc.teamcode.razvan.autoSleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Deprecated @Disabled @Autonomous(name = "Auto_Crater", group = "Autonomous")
public class AutoCrater extends AutonomousOp {

    @Override
    public void runOpMode() {
        super.runOpMode();

        addTelemetryWithUpdate("Status", "Ready to run");
        waitForStart();

        addTelemetryWithUpdate("Direction", "Front");
        moveForward(SPEED * 0.7);

        addTelemetryWithUpdate("Direction", "Back");
        moveForward(SPEED * -0.8, PAUSE / 2);

        addTelemetryWithUpdate("Rotation", "Right");
        rotateRight(SPEED * 1.1, PAUSE / 2);

        addTelemetryWithUpdate("Direction", "Front");
        moveForward(SPEED * 1.1);

        addTelemetryWithUpdate("Rotation", "Left");
        rotateRight(SPEED * -0.75);

        addTelemetryWithUpdate("Direction", "Front");
        moveForward(SPEED * 0.9, PAUSE * 2);

        addTelemetryWithUpdate("Servo", "Moving Forward");
        markerServo.setPosition(1);
        sleep(PAUSE * 3);

        addTelemetryWithUpdate("Servo", "Moving Backwards");
        markerServo.setPosition(0);
        sleep(500);

        addTelemetryWithUpdate("Direction", "Front");
        moveForward(SPEED * -2.5, PAUSE + 200);

        addTelemetryWithUpdate("Status", "Path Completed");
    }
}
