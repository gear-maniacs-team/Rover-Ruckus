package org.firstinspires.ftc.teamcode.razvan.autoSleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Deprecated @Disabled
@Autonomous(name = "Auto_Depot", group = "Autonomous")
public class AutoDepo extends AutonomousOp {

    @Override
    public void runOpMode() {
        super.runOpMode();

        addTelemetryWithUpdate("Status", "Ready to run");
        waitForStart();

        addTelemetryWithUpdate("Status", "Front");
        moveForward(SPEED * 1.1);

        addTelemetryWithUpdate("Servo", "Moving");
        markerServo.setPosition(1);
        sleep(PAUSE * 3);

        addTelemetryWithUpdate("Direction", "Back, Retrieve Servo");
        markerServo.setPosition(0);
        moveForward(SPEED * -0.7);

        addTelemetryWithUpdate("Rotation", "Left");
        rotateRight(SPEED * -0.85);

        addTelemetryWithUpdate("Direction", "Front");
        moveForward(SPEED);

        addTelemetryWithUpdate("Rotation", "Right");
        rotateRight(SPEED * 0.5);

        addTelemetryWithUpdate("Direction", "Front");
        moveForward(SPEED * 2);

        addTelemetryWithUpdate("Status", "Path Completed");
    }
}
