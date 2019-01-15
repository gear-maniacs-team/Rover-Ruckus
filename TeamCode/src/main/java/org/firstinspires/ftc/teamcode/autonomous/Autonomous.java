package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Motors;

public abstract class Autonomous extends OpMode {

    protected Motors motors = null;

    @Override
    public void init() {
        motors = new Motors(hardwareMap.dcMotor);
    }

    void moveForward(long millis)  {
        long end = System.currentTimeMillis() + millis;

        while (end < System.currentTimeMillis())
            motors.moveForward();
    }
}
