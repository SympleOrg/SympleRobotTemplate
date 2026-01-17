package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class SympleServo extends ServoEx {

    public SympleServo(HardwareMap hwMap, String id, double min, double max) {
        super(hwMap, id, min, max);
    }

    public SympleServo(HardwareMap hwMap, String id, double range, AngleUnit angleUnit) {
        super(hwMap, id, range, angleUnit);
    }

    public SympleServo(HardwareMap hwMap, String id) {
        super(hwMap, id);
    }

    @Override
    public void disable() {
        this.getController().setServoPwmDisable(this.getPortNumber());
        super.disable();
    }

    public void enable() {
        this.getController().setServoPwmEnable(this.getPortNumber());
    }
}
