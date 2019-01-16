package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motors {

    public final static double PI_4 = Math.PI / 4;

    public final DcMotor TL;
    public final DcMotor TR;
    public final DcMotor BL;
    public final DcMotor BR;
    public final DcMotor AE;

    public Motors(HardwareMap.DeviceMapping<DcMotor> dcMotors) {
        TL = dcMotors.get("TL");
        TR = dcMotors.get("TR");
        BL = dcMotors.get("BL");
        BR = dcMotors.get("BR");
        AE = dcMotors.get("ArmExtender");
    }

    public void setMode(DcMotor.RunMode mode) {
        TL.setMode(mode);
        TR.setMode(mode);
        BL.setMode(mode);
        BR.setMode(mode);
        AE.setMode(mode);
    }
}
