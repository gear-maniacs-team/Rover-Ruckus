package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.GoldDetectorManager;
import org.firstinspires.ftc.teamcode.motors.WheelMotors;

public abstract class AutonomousOp extends LinearOpMode {

    protected static final double SPEED = 0.4;
    protected static final int PAUSE = 1000;

    protected WheelMotors wheelMotors = null;
    protected Servo markerServo = null;
    protected final GoldDetectorManager detectorManager = new GoldDetectorManager();

    @Override
    public void runOpMode() {
        wheelMotors = new WheelMotors(hardwareMap.dcMotor);
        markerServo = hardwareMap.get(Servo.class, "Marker");
    }

    protected void moveForward(double speed) {
        moveForward(speed, PAUSE);
    }

    protected void moveForward(double speed, int timeout) {
        wheelMotors.TR.setPower(-speed);
        wheelMotors.TL.setPower(speed);
        wheelMotors.BR.setPower(-speed);
        wheelMotors.BL.setPower(speed);

        sleep(timeout);

        wheelMotors.TR.setPower(0);
        wheelMotors.TL.setPower(0);
        wheelMotors.BR.setPower(0);
        wheelMotors.BL.setPower(0);

        sleep(timeout / 2);
    }

    protected void rotateLeft(double speed) {
        rotateLeft(speed, PAUSE);
    }

    protected void rotateLeft(double speed, int timeout) {
        wheelMotors.TR.setPower(speed);
        wheelMotors.TL.setPower(speed);
        wheelMotors.BR.setPower(speed);
        wheelMotors.BL.setPower(speed);

        sleep(timeout);

        wheelMotors.TR.setPower(0);
        wheelMotors.TL.setPower(0);
        wheelMotors.BR.setPower(0);
        wheelMotors.BL.setPower(0);

        sleep(timeout / 2);
    }

    protected void addTelemetryWithUpdate(String caption, String value) {
        telemetry.addData(caption, value);
        telemetry.update();
    }
}
