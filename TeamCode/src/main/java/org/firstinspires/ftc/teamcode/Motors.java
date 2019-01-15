package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motors {

    final static double PI_4 = Math.PI / 4;

    public final DcMotor TL;
    public final DcMotor TR;
    public final DcMotor BL;
    public final DcMotor BR;

    public Motors(HardwareMap.DeviceMapping<DcMotor> dcMotors) {
        TL = dcMotors.get("TL");
        TR = dcMotors.get("TR");
        BL = dcMotors.get("BL");
        BR = dcMotors.get("BR");
    }

    public void moveForward() {
        final double direction = Math.atan2(0, 1) - PI_4;

        final double v1 = Math.cos(direction);
        final double v2 = Math.sin(direction);

        TL.setPower(v1);
        TR.setPower(v2);
        BL.setPower(v2);
        BR.setPower(v1);
    }
}
