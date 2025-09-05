package org.firstinspires.ftc.teamcode.managers;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.RobotConstants.DriveConstants;
import org.firstinspires.ftc.teamcode.maps.SensorMap;
import org.firstinspires.ftc.teamcode.util.MathUtil;

/**
 * Singleton class for managing the robot's position and heading.
 * <p>
 * This class uses a BHI260IMU and three dead-wheel encoders (right, left, back)
 * to track the robot's orientation and approximate movement.
 * </p>
 *
 * <p>Provides methods to get heading from the gyro, heading from wheel encoders,
 * relative heading, wheel distances, and to reset the heading reference.</p>
 */
public class RobotPositionManager {

    /** The BHI260IMU used to measure robot orientation. */
    private final BHI260IMU imu;

    /** Right dead-wheel encoder motor. */
    private final MotorEx rightDeadWheel;

    /** Left dead-wheel encoder motor. */
    private final MotorEx leftDeadWheel;

    /** Back dead-wheel encoder motor. */
    private final MotorEx backDeadWheel;

    /** The robot's initial heading reference (in degrees). */
    private double startingAngle;

    /** Singleton instance of the RobotPositionManager. */
    private static RobotPositionManager instance;

    /**
     * Private constructor for singleton pattern.
     * Initializes IMU and dead-wheel motors, resets encoders, and sets starting angle.
     *
     * @param hardwareMap the hardware map from the OpMode
     */
    private RobotPositionManager(HardwareMap hardwareMap) {
        BHI260IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(DriveConstants.LOGO_FACING_DIRECTION, DriveConstants.USB_FACING_DIRECTION));
        this.imu = hardwareMap.get(BHI260IMU.class, "imu");
        this.imu.initialize(parameters);

        this.rightDeadWheel = new MotorEx(hardwareMap, SensorMap.DEAD_WHEEL_RIGHT.getId());
        this.leftDeadWheel = new MotorEx(hardwareMap, SensorMap.DEAD_WHEEL_LEFT.getId());
        this.backDeadWheel = new MotorEx(hardwareMap, SensorMap.DEAD_WHEEL_BACK.getId());

        this.rightDeadWheel.encoder.setDirection(Motor.Direction.REVERSE);
        this.leftDeadWheel.encoder.setDirection(Motor.Direction.REVERSE);
//        this.rightDeadWheel.encoder.setDirection(Motor.Direction.REVERSE);

        this.rightDeadWheel.resetEncoder();
        this.leftDeadWheel.resetEncoder();
        this.backDeadWheel.resetEncoder();

        this.startingAngle = getHeadingByGyro();
    }

    /**
     * Initializes the singleton instance of the RobotPositionManager.
     *
     * @param hardwareMap the hardware map from the OpMode
     */
    public static void init(HardwareMap hardwareMap) {
        instance = new RobotPositionManager(hardwareMap);
    }

    /**
     * Returns the singleton instance of the RobotPositionManager.
     *
     * @return the instance
     */
    public static RobotPositionManager getInstance() {
        return instance;
    }

    /**
     * Returns the robot's heading based on the gyro (IMU).
     *
     * @return heading in degrees
     */
    public double getHeadingByGyro() {
        return this.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    /**
     * Returns the robot's heading relative to the starting angle.
     *
     * @return relative heading in degrees
     */
    public double getRelativeHeading() {
        return this.getHeadingByGyro() - this.startingAngle;
    }

    /**
     * Calculates the robot's heading based on the left and right dead-wheel distances.
     *
     * @return heading in degrees
     */
    public double getHeadingByWheels() {
        double right = this.getRightWheelDistanceDriven();
        double left = this.getLeftWheelDistanceDriven();
        return Math.toDegrees((right - left) / DriveConstants.WHEELS_DISTANCE);
    }

    /** Resets the heading reference to the current IMU heading. */
    public void resetHeading() {
        this.startingAngle = getHeadingByGyro();
    }

    /** @return distance driven by the left dead-wheel in meters */
    public double getLeftWheelDistanceDriven() {
        return this.encoderTicksToMeter(this.leftDeadWheel.getCurrentPosition());
    }

    /** @return distance driven by the right dead-wheel in meters */
    public double getRightWheelDistanceDriven() {
        return this.encoderTicksToMeter(this.rightDeadWheel.getCurrentPosition());
    }

    /** @return distance driven by the back dead-wheel in meters */
    public double getBackWheelDistanceDriven() {
        return this.encoderTicksToMeter(this.backDeadWheel.getCurrentPosition());
    }

    /**
     * Converts encoder ticks to meters using constants from {@link DriveConstants}.
     * @see MathUtil#encoderTicksToMeter(double, double, double, double)  
     * 
     * @param ticks encoder ticks
     * @return distance in meters
     */
    private double encoderTicksToMeter(double ticks) {
        return MathUtil.encoderTicksToMeter(ticks, DriveConstants.WHEEL_RADIUS, DriveConstants.TICKS_PER_REV, DriveConstants.GEAR_RATIO);
    }
}
