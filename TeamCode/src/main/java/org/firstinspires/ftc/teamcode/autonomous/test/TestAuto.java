package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.autonomous.EncodersAuto;

@Disabled
@Autonomous(name = "MotorTest", group = "Test")
public class TestAuto extends EncodersAuto {

    @Override
    protected boolean isVuforiaEnabled() {
        return false;
    }

    @Override
    protected void onStart() {
        moveForward(500);
    }
}
