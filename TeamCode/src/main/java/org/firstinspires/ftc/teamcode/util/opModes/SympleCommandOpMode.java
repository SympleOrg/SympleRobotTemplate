package org.firstinspires.ftc.teamcode.util.opModes;

import com.arcrobotics.ftclib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.RobotControllerBase;

import top.symple.symplegraphdisplay.SympleGraphDisplay;

/**
 * Abstract base class for a Symple Command-based OpMode.
 *
 * <p>This class extends {@link CommandOpMode} and provides a structured lifecycle
 * for robot operation using a {@link RobotControllerBase}. It automatically handles:
 * <ul>
 *     <li>Initialization and key binding creation</li>
 *     <li>Init-loop execution with telemetry and graph updates</li>
 *     <li>Post-initialize and run-loop execution</li>
 *     <li>Exception logging via {@link org.firstinspires.ftc.teamcode.util.DataLogger}</li>
 * </ul>
 * </p>
 *
 * <p>Subclasses must set {@link #robotController} to an instance of their robot controller
 * before starting the OpMode.</p>
 */
public abstract class SympleCommandOpMode extends CommandOpMode {

    /**
     * The robot controller managing key bindings, initialization, and periodic updates.
     * <p>Must be initialized before starting the OpMode.</p>
     */
    protected RobotControllerBase robotController;

    /**
     * Main OpMode lifecycle.
     * <p>
     * Handles initialization, init-loop, post-initialize, active run-loop, and cleanup.
     * Exceptions during runtime are logged to the robot's {@link org.firstinspires.ftc.teamcode.util.DataLogger} if available.
     * </p>
     */
    @Override
    public void runOpMode() {
        try {
            // Call the subclass's initialize method
            this.initialize();

            if(robotController == null) {
                telemetry.addData("Err", "Please initialize the robot first");
                this.reset();
                return;
            }

            // Setup key bindings and initialization
            robotController.createKeyBindings();
            robotController.initialize();

            // Init-mode loop
            while (this.opModeInInit() && !this.isStopRequested()) {
                robotController.initializeLoop();
                SympleGraphDisplay.getInstance().run();
                this.robotController.getTelemetry().update();
            }

            // Wait for the start button
            this.waitForStart();

            // Post-initialize actions
            robotController.postInitialize();

            // Active run-loop
            while (!isStopRequested() && opModeIsActive()) {
                this.run(); // Call subclass run
                robotController.run(); // Run robot logic
                SympleGraphDisplay.getInstance().run(); // Update graphs
                this.robotController.getTelemetry().update(); // Update telemetry
            }

            // Cleanup after run
            robotController.postRun();
        } catch (Exception exception) {
            // Log exceptions if robotController is available
            if(robotController != null) {
                robotController.getDataLogger().addThrowable(exception);
            }
            throw exception;
        }

        // Reset the OpMode
        this.reset();
    }
}
