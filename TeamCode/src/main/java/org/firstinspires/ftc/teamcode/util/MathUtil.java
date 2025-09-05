package org.firstinspires.ftc.teamcode.util;

public class MathUtil {
    public static double ticksToDeg(int ticks, double maxTicks) {
        return ((ticks * 360) / maxTicks);
    }

    public static double degToTicks(double deg, double maxTicks) {
        return ((deg * maxTicks) / 360.0f);
    }

    public static double encoderTicksToMeter(double ticks, double wheelRadius, double ticksPerRev, double gearRatio) {
        return (Math.PI * 2 * wheelRadius) / (ticksPerRev * gearRatio) * ticks;
    }
}
