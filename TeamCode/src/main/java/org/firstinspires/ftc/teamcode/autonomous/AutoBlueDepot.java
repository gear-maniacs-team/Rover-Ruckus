package org.firstinspires.ftc.teamcode.autonomous;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "AutoBlueDepot", group = "Autonomous")
public class AutoBlueDepot extends Autonomous {

    @Override
    public void start() {
        super.start();

    }

    @Override
    public void loop() {
        moveForward(1000);
        stop();
    }

    @Override
    public void stop() {
    }
}
