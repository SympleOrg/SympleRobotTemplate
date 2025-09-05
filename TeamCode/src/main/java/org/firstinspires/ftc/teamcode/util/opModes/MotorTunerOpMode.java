package org.firstinspires.ftc.teamcode.util.opModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.maps.MotorMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An {@link com.qualcomm.robotcore.eventloop.opmode.OpMode} for tuning and testing motors.
 *
 * <p>This OpMode integrates with the FTC Dashboard via {@link com.acmerobotics.dashboard.config.Config},
 * allowing the user to select a motor and set its power dynamically from the dashboard UI.</p>
 *
 * <p>Workflow:</p>
 * <ol>
 *   <li>Driver Station: select <b>Motor Tuner</b> TeleOp and press INIT.</li>
 *   <li>Dashboard: adjust {@link #motorId} and {@link #power} values.</li>
 *   <li>OpMode shows motor list and highlights the current motor in telemetry.</li>
 *   <li>Press PLAY: the motor is initialized, and pressing <b>X</b> on gamepad2
 *       applies the configured {@link #power}.</li>
 * </ol>
 */
@Config
@TeleOp(name = "Motor Tuner", group = "tune")
public class MotorTunerOpMode extends CommandOpMode {

    /**
     * A mapping from integer IDs to available motors from {@link MotorMap}.
     * This enables selection of motors by ID via the dashboard.
     */
    private static final HashMap<Integer, MotorMap> MOTORS = new HashMap<>();

    static {
        MotorMap[] motors = MotorMap.values();
        for (int i = 0; i <motors.length; i++) {
            MOTORS.put(i, motors[i]);
        }
    }

    /**
     * The ID of the motor currently being tuned.
     * <p>Adjustable from FTC Dashboard.</p>
     */
    public static int motorId = 0;

    /**
     * The power to apply to the selected motor when activated.
     * <p>Adjustable from FTC Dashboard.</p>
     */
    public static double power = 0;

    private GamepadEx gamepadEx;
    private MotorMap motorName;
    private MotorEx motor;

    private final AtomicReference<Double> atomicPower = new AtomicReference<>(power);

    @Override
    public void initialize() {
        this.gamepadEx = new GamepadEx(gamepad2);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    private void initializeLoop() {
        telemetry.addData("Motor List", " ");
        for(Map.Entry<Integer, MotorMap> entry : MOTORS.entrySet()) {
            telemetry.addData(entry.getKey().toString(), entry.getValue().getId());
        }

        MotorMap currentMotor = MOTORS.get(motorId);
        if(currentMotor != null) {
            this.motorName = currentMotor;
        }

        telemetry.addData("Current Motor", motorName != null ? motorName.getId() : "Unknown");
        telemetry.update();
    }

    private void postInitialize() {
        motor = new MotorEx(hardwareMap, motorName.getId());

        gamepadEx.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(new RunCommand(() -> this.motor.set(atomicPower.get())));
    }

    @Override
    public void run() {
        super.run();
        atomicPower.set(power);
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
