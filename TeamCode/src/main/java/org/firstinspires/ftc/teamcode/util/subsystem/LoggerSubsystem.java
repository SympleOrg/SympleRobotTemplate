package org.firstinspires.ftc.teamcode.util.subsystem;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.teamcode.util.DataLogger;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * A subsystem interface that provides access to logging utilities.
 *
 * <p>Implement this interface in subsystems that need to report
 * both runtime telemetry (to the driver station or dashboard)
 * and persistent log data (to files via {@link DataLogger}).</p>
 *
 * <p>This allows consistent logging across all subsystems in the robot.</p>
 */
public interface LoggerSubsystem {
    /**
     * Returns the telemetry object for real-time data reporting.
     * <p>This is typically a {@link Telemetry}
     * wrapped inside a {@link MultipleTelemetry} instance.</p>
     *
     * @return the telemetry instance used for reporting data to the driver station/dashboard
     */
    MultipleTelemetry getTelemetry();
    /**
     * Returns the persistent data logger used for file-based logging.
     *
     * @return the {@link DataLogger} instance for this subsystem
     */
    DataLogger getDataLogger();
}
