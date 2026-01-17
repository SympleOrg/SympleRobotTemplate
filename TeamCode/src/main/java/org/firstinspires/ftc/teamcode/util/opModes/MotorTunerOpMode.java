package org.firstinspires.ftc.teamcode.util.opModes;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.maps.MotorMap;

/**
 * An {@link com.qualcomm.robotcore.eventloop.opmode.OpMode} for tuning and testing motors.
 *
 * <p>This OpMode integrates with FTC Panels via {@link Configurable},
 * allowing the user to select a motor and set its power dynamically from the Panels UI.</p>
 *
 * <p>Workflow:</p>
 * <ol>
 *   <li>Driver Station: select <b>Motor Tuner</b> TeleOp and press INIT.</li>
 *   <li>Panels: adjust {@link #motorId} and {@link #power} values.</li>
 *   <li>Press PLAY: the motor is initialized and running with the applied configured {@link #power}.</li>
 * </ol>
 */
@Configurable
@TeleOp(name = "Motor Tuner", group = "tune")
public class MotorTunerOpMode extends CommandOpMode {
    /**
     * The power to apply to the selected motor when activated.
     * <p>Adjustable from Panels.</p>
     */
    public static double power = 0;

    /**
     * The ID of the motor currently being tuned.
     * <p>Adjustable from Panels.</p>
     */
    public static MotorMap motorId;

    private GamepadEx gamepadEx;
    private MotorEx motor;

    @Override
    public void initialize() {
        this.gamepadEx = new GamepadEx(gamepad2);

        telemetry = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);
    }

    private void initializeLoop() {
        telemetry.addData("Current Motor", motorId != null ? motorId.getId() : "Unknown");
        telemetry.update();
    }

    private void postInitialize() {
        if (motorId == null) throw new RuntimeException("Invalid motor!");

        motor = new MotorEx(hardwareMap, motorId.getId());
    }

    @Override
    public void run() {
        super.run();

        this.motor.set(power);

        telemetry.addData("power", power);
        telemetry.update();
    }

    @Override
    public void runOpMode() {
        this.initialize();

        // runs when in init mode
        while (this.opModeInInit() && !this.isStopRequested()) {
            initializeLoop();
        }

        this.waitForStart();

        postInitialize();

        // run the scheduler
        while (!isStopRequested() && opModeIsActive()) {
            this.run();
        }

        this.reset();
    }
}
