package org.firstinspires.ftc.teamcode.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmMotors {

    public final DcMotor angle;
    public final DcMotor extender;
    public final DcMotor collector;

    public ArmMotors(HardwareMap.DeviceMapping<DcMotor> dcMotors) {
        angle = dcMotors.get("ArmAngle");
        extender = dcMotors.get("ArmExtender");
        collector = dcMotors.get("ArmExtender");
    }
}

