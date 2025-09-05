package org.firstinspires.ftc.teamcode.util;

public class MathUtil {
    /**
     * Converts encoder ticks to degrees of rotation.
     *
     * @param ticks    the number of encoder ticks
     * @param maxTicks the total number of ticks per full rotation (360°)
     * @return the corresponding angle in degrees
     */
    public static double ticksToDeg(int ticks, double maxTicks) {
        return ((ticks * 360) / maxTicks);
    }


    /**
     * Converts an angle in degrees to encoder ticks.
     *
     * @param deg      the angle in degrees
     * @param maxTicks the total number of ticks per full rotation (360°)
     * @return the corresponding encoder tick count
     */
    public static double degToTicks(double deg, double maxTicks) {
        return ((deg * maxTicks) / 360.0f);
    }

    /**
     * Converts encoder ticks into linear distance traveled by a wheel.
     *
     * <p>The formula accounts for wheel circumference and gear ratio.</p>
     *
     * @param ticks       the number of encoder ticks
     * @param wheelRadius the radius of the wheel (in meters)
     * @param ticksPerRev the number of encoder ticks per full revolution
     * @param gearRatio   the gear ratio (output revolutions / input revolutions).
     *                    Use 1.0 if the encoder is mounted directly on the wheel shaft.
     * @return the distance traveled in meters
     */
    public static double encoderTicksToMeter(double ticks, double wheelRadius, double ticksPerRev, double gearRatio) {
        return (Math.PI * 2 * wheelRadius) / (ticksPerRev * gearRatio) * ticks;
    }
}
