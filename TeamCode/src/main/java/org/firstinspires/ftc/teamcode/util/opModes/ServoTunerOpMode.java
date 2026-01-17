package org.firstinspires.ftc.teamcode.util.opModes;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.maps.ServoMap;
import org.firstinspires.ftc.teamcode.util.SympleServo;

/**
 * An {@link com.qualcomm.robotcore.eventloop.opmode.OpMode} for tuning and testing servos.
 *
 * <p>This OpMode integrates with Panels via {@link Configurable},
 * allowing the user to select a servo and set its target angle dynamically from the Panels UI.</p>
 *
 * <p>Workflow:</p>
 * <ol>
 *     <li>Driver Station: select <b>Servo Tuner</b> TeleOp and press INIT.</li>
 *     <li>Panels: adjust {@link #servoId} and {@link #angle} values.</li>
 *     <li>Press PLAY: the servo is initialized and moves the configured {@link #angle}.</li>
 * </ol>
 */
@Configurable
@TeleOp(name = "Servo Tuner", group = "tune")
public class ServoTunerOpMode extends CommandOpMode {

    /**
     * The ID of the servo currently being tuned.
     * <p>Adjustable from Panels.</p>
     */
    public static ServoMap servoId;

    /**
     * The target angle to apply to the selected servo when activated.
     * <p>Adjustable from Panels.</p>
     */
    public static double angle = 0;

    private GamepadEx gamepadEx;
    private SympleServo servo;

    @Override
    public void initialize() {
        this.gamepadEx = new GamepadEx(gamepad2);

        telemetry = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);
    }

    private void initializeLoop() {
        telemetry.addData("Current Servo", servoId != null ? servoId.getId() : "Unknown");
        telemetry.update();
    }

    private void postInitialize() {
        if (servoId == null) throw new RuntimeException("Invalid servo!");

        servo = new SympleServo(hardwareMap, servoId.getId(), 0.0, 300.0);
    }

    @Override
    public void run() {
        super.run();

        servo.set(angle);

        telemetry.addData("angle", angle);
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
