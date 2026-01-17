package org.firstinspires.ftc.teamcode.util.drivetrain;

import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import java.util.HashMap;

/**
 * Represents a set of four Mecanum wheels on a robot chassis.
 * <p>
 * Provides convenient methods to control motor powers, set inversion, and configure
 * zero power behavior for each wheel individually or in groups (all wheels or sides).
 * </p>
 */
public class MecanumWheelSet {

    /**
     * Mapping from each {@link MecanumWheel} to its corresponding {@link MotorEx}.
     */
    private final HashMap<MecanumWheel, MotorEx> motors;

    /**
     * Constructs a MecanumWheelSet with four motors.
     *
     * @param frontLeft  motor for the front-left wheel
     * @param frontRight motor for the front-right wheel
     * @param backLeft   motor for the back-left wheel
     * @param backRight  motor for the back-right wheel
     */
    public MecanumWheelSet(
            MotorEx frontLeft, MotorEx frontRight,
            MotorEx backLeft, MotorEx backRight
    ) {
        this.motors = new HashMap<>();

        this.motors.put(MecanumWheel.FRONT_LEFT, frontLeft);
        this.motors.put(MecanumWheel.FRONT_RIGHT, frontRight);
        this.motors.put(MecanumWheel.BACK_LEFT, backLeft);
        this.motors.put(MecanumWheel.BACK_RIGHT, backRight);
    }

    /**
     * Retrieves the motor associated with a specific wheel.
     *
     * @param wheel the wheel whose motor to get
     * @return the motor for the specified wheel
     * @throws RuntimeException if the motor is not initialized
     */
    private MotorEx getMotor(MecanumWheel wheel) {
        MotorEx motor = this.motors.get(wheel);
        if(motor == null) throw new RuntimeException("Mecanum motor '" + wheel.name() + "' is not initialized.");
        return motor;
    }

    /**
     * Sets the same power to all four wheels.
     *
     * @param power the power to apply (-1.0 to 1.0)
     */
    public void setPower(double power) {
        this.setPower(MecanumWheel.FRONT_LEFT, power);
        this.setPower(MecanumWheel.FRONT_RIGHT, power);
        this.setPower(MecanumWheel.BACK_LEFT, power);
        this.setPower(MecanumWheel.BACK_RIGHT, power);
    }

    /**
     * Sets power for the left and right sides independently.
     *
     * @param left  power for left-side wheels
     * @param right power for right-side wheels
     */
    public void setSidePower(double left, double right) {
        this.setPower(MecanumWheel.FRONT_LEFT, left);
        this.setPower(MecanumWheel.FRONT_RIGHT, right);
        this.setPower(MecanumWheel.BACK_LEFT, left);
        this.setPower(MecanumWheel.BACK_RIGHT, right);
    }

    /**
     * Sets the power of a single wheel.
     *
     * @param wheel the wheel to set
     * @param power the power to apply (-1.0 to 1.0)
     */
    public void setPower(MecanumWheel wheel, double power) {
        this.getMotor(wheel).set(power);
    }

    /**
     * Sets the power of all wheels based on a {@link MecanumChassisUtils.MecanumWheelSpeeds} object.
     *
     * @param wheelSpeeds the desired wheel speeds
     */
    public void setPower(MecanumChassisUtils.MecanumWheelSpeeds wheelSpeeds) {
        this.setPower(MecanumWheel.FRONT_LEFT, wheelSpeeds.getFrontLeft());
        this.setPower(MecanumWheel.FRONT_RIGHT, wheelSpeeds.getFrontRight());
        this.setPower(MecanumWheel.BACK_LEFT, wheelSpeeds.getBackLeft());
        this.setPower(MecanumWheel.BACK_RIGHT, wheelSpeeds.getBackRight());
    }

    /**
     * Inverts a specific wheel’s direction.
     *
     * @param wheel    the wheel to invert
     * @param inverted true to invert, false to normal
     */
    public void setInverted(MecanumWheel wheel, boolean inverted) {
        this.getMotor(wheel).setInverted(inverted);
    }

    /**
     * Sets the zero power behavior for all wheels.
     *
     * @param zeroPowerBehavior the behavior to apply when power is zero
     */
    public void setZeroPowerBehavior(Motor.ZeroPowerBehavior zeroPowerBehavior) {
        this.setZeroPowerBehavior(MecanumWheel.FRONT_LEFT, zeroPowerBehavior);
        this.setZeroPowerBehavior(MecanumWheel.FRONT_RIGHT, zeroPowerBehavior);
        this.setZeroPowerBehavior(MecanumWheel.BACK_LEFT, zeroPowerBehavior);
        this.setZeroPowerBehavior(MecanumWheel.BACK_RIGHT, zeroPowerBehavior);
    }

    /**
     * Sets the zero power behavior for a specific wheel.
     *
     * @param wheel            the wheel to configure
     * @param zeroPowerBehavior the behavior to apply when power is zero
     */
    public void setZeroPowerBehavior(MecanumWheel wheel, Motor.ZeroPowerBehavior zeroPowerBehavior) {
        this.getMotor(wheel).setZeroPowerBehavior(zeroPowerBehavior);
    }

    /**
     * Represents the four wheels of a Mecanum drivetrain.
     */
    public enum MecanumWheel {
        FRONT_LEFT,
        FRONT_RIGHT,
        BACK_LEFT,
        BACK_RIGHT
    }
}
