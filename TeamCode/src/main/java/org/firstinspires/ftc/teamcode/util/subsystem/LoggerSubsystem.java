package org.firstinspires.ftc.teamcode.util.subsystem;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.teamcode.util.DataLogger;

public interface LoggerSubsystem {
    MultipleTelemetry getTelemetry();
    DataLogger getDataLogger();
}
