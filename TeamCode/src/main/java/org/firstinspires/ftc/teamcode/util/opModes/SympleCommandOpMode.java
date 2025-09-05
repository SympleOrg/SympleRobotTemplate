package org.firstinspires.ftc.teamcode.util.opModes;

import com.arcrobotics.ftclib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.RobotControllerBase;

import top.symple.symplegraphdisplay.SympleGraphDisplay;

public abstract class SympleCommandOpMode extends CommandOpMode {
    protected RobotControllerBase robotController;

    @Override
    public void runOpMode() {
        try {
            this.initialize();

            if(robotController == null) {
                telemetry.addData("Err", "Please initialize the robot first");
                this.reset();
                return;
            }

            robotController.createKeyBindings();
            robotController.initialize();

            // runs when in init mode
            while (this.opModeInInit() && !this.isStopRequested()) {
                robotController.initializeLoop();
                SympleGraphDisplay.getInstance().run();
                this.robotController.getTelemetry().update();
            }

            this.waitForStart();

            robotController.postInitialize();

            // run the scheduler
            while (!isStopRequested() && opModeIsActive()) {
                this.run();
                robotController.run();
                SympleGraphDisplay.getInstance().run();
                this.robotController.getTelemetry().update();
            }

            robotController.postRun();
        } catch (Exception exception) {
            if(robotController != null) {
                robotController.getDataLogger().addThrowable(exception);
            }
            throw exception;
        }

        this.reset();
    }
}
