package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

public class RobotConstants {
    public static class DriveConstants {
        public static final double TICKS_PER_REV = 2000;

        public static final double GEAR_RATIO = 1;
        public static final double WHEEL_RADIUS = 0.024;
        public static final double WHEELS_DISTANCE = 0.207;

        public static final double METERS_PER_REV = (Math.PI * 2) * WHEEL_RADIUS;
        public static final double METERS_PER_TICK = (METERS_PER_REV / (TICKS_PER_REV * GEAR_RATIO));

        public static final double Ks = 0;

        public static final RevHubOrientationOnRobot.LogoFacingDirection LOGO_FACING_DIRECTION = RevHubOrientationOnRobot.LogoFacingDirection.LEFT;
        public static final RevHubOrientationOnRobot.UsbFacingDirection USB_FACING_DIRECTION = RevHubOrientationOnRobot.UsbFacingDirection.UP;

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
