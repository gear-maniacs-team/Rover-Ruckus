package org.firstinspires.ftc.teamcode.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmMotors {

    public final DcMotor latchMotor;
    public final DcMotor armAngle;
    public final DcMotor armExtension;
    public final DcMotor collector;

    public ArmMotors(HardwareMap.DeviceMapping<DcMotor> dcMotors) {
        latchMotor = dcMotors.get("LatchMotor");
        armAngle = dcMotors.get("ArmAngle");
        armExtension = dcMotors.get("ArmExtension");
        collector = dcMotors.get("Collector");
    }
}
