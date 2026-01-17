package org.firstinspires.ftc.teamcode;

import com.bylazar.camerastream.PanelsCameraStream;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.managers.RobotPositionManager;
import org.firstinspires.ftc.teamcode.util.DataLogger;
import org.firstinspires.ftc.teamcode.util.opModes.SympleCommandOpMode;

public abstract class RobotControllerBase {
    public final GamepadEx driverController;
    public final GamepadEx actionController;

    private final HardwareMap hardwareMap;
    private final JoinedTelemetry telemetry;
    private final DataLogger dataLogger;

    public RobotControllerBase(HardwareMap hMap, Telemetry telemetry, Gamepad driverController, Gamepad actionController, String logFilePrefix, boolean logData) {
        this.hardwareMap = hMap;
        this.telemetry = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);
        this.dataLogger = new DataLogger(logFilePrefix, !logData);

        this.dataLogger.addData(DataLogger.DataType.INFO, "RobotController: Initializing...");

        this.driverController = new GamepadEx(driverController);
        this.actionController = new GamepadEx(actionController);

        // Reset the robot state
        this.dataLogger.addData(DataLogger.DataType.INFO, "RobotController: resetting robot");
        PanelsCameraStream.INSTANCE.stopStream();
        CommandScheduler.getInstance().reset();
        RobotPositionManager.init(hardwareMap);
    }

    /**
     * Register all robot keybinds here.
     * <p>
     * Subclasses should implement this method to define all gamepad or controller bindings
     * for robot actions.
     * </p>
     */
    public abstract void createKeyBindings();

    /**
     * Called once when the user presses the **Init** button.
     * Use this to perform setup tasks before the robot starts running.
     */
    public abstract void initialize();

    /**
     * Called repeatedly while the robot is in the **Init** phase,
     * after {@link #initialize()} has been executed.
     * Use this perform pre-start checks.
     */
    public abstract void initializeLoop();

    /**
     * Called once when the user presses the **Play** button.
     * Use this for any final setup before the main loop starts.
     */
    public abstract void postInitialize();

    /**
     * Called repeatedly while the robot is running (after the **Play** button is pressed).
     * This is the main loop for robot control logic during a match.
     */
    public abstract void run();

    /**
     * Called once when the robot is stopped (after the **Stop** button is pressed).
     * Use this to release resources or reset hardware states.
     */
    public abstract void postRun();

    /**
     * See {@link Telemetry} for all the docs.
     * @return {@link JoinedTelemetry}
     */
    public JoinedTelemetry getTelemetry() {
        return telemetry;
    }

    /**
     * See {@link HardwareMap} for all the docs.
     * @return {@link HardwareMap}
     */
    public HardwareMap getHardwareMap() {
        return hardwareMap;
    }

    /**
     * See {@link HardwareMap} for all the docs.
     * @return {@link DataLogger}
     */
    public DataLogger getDataLogger() {
        return dataLogger;
    }

    protected static abstract class Builder {
        protected HardwareMap hardwareMap;
        protected Telemetry telemetry;
        protected Gamepad driverController;
        protected Gamepad actionController;
        protected String logFilePrefix;
        protected boolean logData;

        public Builder() {
            this.logData = true;
            this.logFilePrefix = "RobotController";
        }

        public Builder initializeDefaults(SympleCommandOpMode opMode) {
            this.hardwareMap = opMode.hardwareMap;
            this.telemetry = opMode.telemetry;
            this.driverController = opMode.gamepad1;
            this.actionController = opMode.gamepad2;
            return this;
        }

        public abstract RobotControllerBase build();
    }
}
