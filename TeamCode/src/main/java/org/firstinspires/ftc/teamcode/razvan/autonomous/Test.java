package org.firstinspires.ftc.teamcode.razvan.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name = "MotorTest")
public class Test extends EncodersAuto {

    @Override
    protected void onStart() {
        stopCamera();
        wheelMotors.setPowerAll(1);
        sleep(8000);
    }
}
