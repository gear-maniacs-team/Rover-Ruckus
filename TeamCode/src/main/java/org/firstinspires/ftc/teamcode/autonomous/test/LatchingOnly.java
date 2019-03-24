package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.EncodersAuto;

@Autonomous(name = "LatchingOnly", group = "Test")
public class LatchingOnly extends EncodersAuto {

    @Override
    protected boolean isVuforiaEnabled() {
        return false;
    }

    @Override
    protected void onStart() {
        lowerRobot();
    }
}
