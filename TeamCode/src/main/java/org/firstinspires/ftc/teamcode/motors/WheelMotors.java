package org.firstinspires.ftc.teamcode.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class WheelMotors {

    private final static double CIRC = 31.9024;
    private final static double TICKS_PER_ROTATION = 1120;
    private final static double TICKS = CIRC / TICKS_PER_ROTATION;
    public final static double PI_4 = Math.PI / 4;

    public final DcMotor TL;
    public final DcMotor TR;
    public final DcMotor BL;
    public final DcMotor BR;

    public WheelMotors(HardwareMap.DeviceMapping<DcMotor> dcMotors) {
        TL = dcMotors.get("TL");
        TR = dcMotors.get("TR");
        BL = dcMotors.get("BL");
        BR = dcMotors.get("BR");
    }

    public void setMode(DcMotor.RunMode mode) {
        TL.setMode(mode);
        TR.setMode(mode);
        BL.setMode(mode);
        BR.setMode(mode);
    }

    public void setTargetPosition(int position) {
        TL.setTargetPosition(position);
        TR.setTargetPosition(position);
        BL.setTargetPosition(position);
        BR.setTargetPosition(position);
    }

    public void setPower(double power) {
        TL.setPower(power);
        TR.setPower(power);
        BL.setPower(power);
        BR.setPower(power);
    }

    public int getTicks(int cm) {
        return cm * (int) TICKS;
    }
}
