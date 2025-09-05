package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

/**
 * Contains all robot-wide constants for the robot.
 */
public class RobotConstants {

    /**
     * Constants related to the robot's drivetrain.
     */
    public static class DriveConstants {
        /** Number of encoder ticks per motor revolution. */
        public static final double TICKS_PER_REV = 2000;

        /** Gear ratio from motor to wheel (output/input). */
        public static final double GEAR_RATIO = 1;

        /** Radius of the wheels in meters. */
        public static final double WHEEL_RADIUS = 0.024;

        /** Distance between left and right wheels (track width) in meters. */
        public static final double WHEELS_DISTANCE = 0.207;

        /** Meters traveled per wheel revolution. */
        public static final double METERS_PER_REV = (Math.PI * 2) * WHEEL_RADIUS;

        /** Meters traveled per encoder tick. */
        public static final double METERS_PER_TICK = (METERS_PER_REV / (TICKS_PER_REV * GEAR_RATIO));

        /** Feedforward constant (Ks) */
        public static final double Ks = 0;

        /** Orientation of the REV Hub logo on the robot. */
        public static final RevHubOrientationOnRobot.LogoFacingDirection LOGO_FACING_DIRECTION = RevHubOrientationOnRobot.LogoFacingDirection.LEFT;

        /** Orientation of the USB ports on the REV Hub. */
        public static final RevHubOrientationOnRobot.UsbFacingDirection USB_FACING_DIRECTION = RevHubOrientationOnRobot.UsbFacingDirection.UP;

        /**
         * Predefined drive speed modes with a modifier for scaling motor power.
         */
        public enum DriveSpeed {
            NORMAL(1),
            SLOW(0.65);

            private final double modifier;

            DriveSpeed(double modifier) {
                this.modifier = modifier;
            }

            public double getSpeedModifier() {
                return modifier;
            }
        }
    }
}
