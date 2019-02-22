package org.firstinspires.ftc.teamcode.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class WheelMotors {

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

    public void setModeAll(DcMotor.RunMode mode) {
        TL.setMode(mode);
        TR.setMode(mode);
        BL.setMode(mode);
        BR.setMode(mode);
    }

    public void setTargetPositionAll(int position) {
        TL.setTargetPosition(position);
        TR.setTargetPosition(position);
        BL.setTargetPosition(position);
        BR.setTargetPosition(position);
    }

    public void setPowerAll(double power) {
        TL.setPower(power);
        TR.setPower(power);
        BL.setPower(power);
        BR.setPower(power);
    }
}
