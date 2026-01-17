package org.firstinspires.ftc.teamcode.subsystems.driveTrain;

import com.seattlesolvers.solverslib.command.Subsystem;

import org.firstinspires.ftc.teamcode.util.subsystem.LoggerSubsystem;

public interface IDriveTrainSubsystem extends LoggerSubsystem, Subsystem {
    void moveSideMotors(double left, double right);
    double getForwardDistanceDriven();
    double getHeading();
}
