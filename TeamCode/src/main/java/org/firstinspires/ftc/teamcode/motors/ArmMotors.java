package org.firstinspires.ftc.teamcode.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmMotors {

    public final DcMotor latchMotor;
    //public final DcMotor armExtender;

    public ArmMotors(HardwareMap.DeviceMapping<DcMotor> dcMotors) {
        latchMotor = dcMotors.get("LatchMotor");
        //armExtender = dcMotors.get("ArmExtender");
    }
}
