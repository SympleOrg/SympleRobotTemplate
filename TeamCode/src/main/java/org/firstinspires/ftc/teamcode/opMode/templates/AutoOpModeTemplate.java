package org.firstinspires.ftc.teamcode.opMode.templates;

import org.firstinspires.ftc.teamcode.subsystems.MechanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.util.opModes.OpModeTemplate;

public class AutoOpModeTemplate extends OpModeTemplate {
    public AutoOpModeTemplate() {
        super(
//            TankDriveBaseSubsystem.class
                MechanumDriveSubsystem.class
        );
    }
}