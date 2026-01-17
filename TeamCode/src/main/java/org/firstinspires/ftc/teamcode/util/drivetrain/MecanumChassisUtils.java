package org.firstinspires.ftc.teamcode.util.drivetrain;

import com.seattlesolvers.solverslib.geometry.Vector2d;

/**
 * Utility class for Mecanum drivetrain calculations.
 *
 * <p>Provides methods to convert chassis movement vectors and rotation
 * into individual wheel speeds, normalize them, and manage wheel speed arrays.</p>
 */
public class MecanumChassisUtils {

    /**
     * Converts a chassis motion vector and rotational speed into
     * individual Mecanum wheel speeds.
     *
     * <p>The vector represents horizontal (x) and vertical (y) movement
     * in the robot-centric coordinate system, and rSpeed represents
     * rotational speed (positive = clockwise).</p>
     *
     * @param vector chassis movement vector (hSpeed, vSpeed)
     * @param rSpeed rotational speed
     * @return a {@link MecanumWheelSpeeds} object containing individual wheel powers
     */
    public static MecanumWheelSpeeds chassisSpeedToWheelSpeeds(Vector2d vector, double rSpeed) {
        double[] speeds = new double[4];
        speeds[0] = Math.sin(vector.angle() + Math.PI / 4) + rSpeed; // front left
        speeds[1] = Math.sin(vector.angle() - Math.PI / 4) - rSpeed; // front right
        speeds[2] = Math.sin(vector.angle() - Math.PI / 4) + rSpeed; // back left
        speeds[3] = Math.sin(vector.angle() + Math.PI / 4) - rSpeed; // back right

        normalize(speeds, vector.magnitude());

        speeds[0] += rSpeed;
        speeds[1] -= rSpeed;
        speeds[2] += rSpeed;
        speeds[3] -= rSpeed;

        normalize(speeds);

        return new MecanumWheelSpeeds(speeds);
    }

    /**
     * Normalizes wheel speeds so the maximum magnitude equals the given value.
     *
     * @param wheelSpeeds array of wheel speeds to normalize
     * @param mag         the target maximum magnitude
     */
    private static void normalize(double[] wheelSpeeds, double mag) {
        double maxSpeed = getMaxSpeeds(wheelSpeeds);

        for (int i = 0; i < wheelSpeeds.length; i++) {
            wheelSpeeds[i] = (wheelSpeeds[i] / maxSpeed) * mag;
        }
    }

    /**
     * Normalizes wheel speeds to ensure none exceeds magnitude 1.
     *
     * @param wheelSpeeds array of wheel speeds to normalize
     */
    private static void normalize(double[] wheelSpeeds) {
        double maxSpeed = getMaxSpeeds(wheelSpeeds);

        if(maxSpeed > 1) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] /= maxSpeed;
            }
        }
    }

    /**
     * Returns the maximum absolute value of wheel speeds.
     *
     * @param wheelSpeeds array of wheel speeds
     * @return maximum absolute speed
     */
    private static double getMaxSpeeds(double[] wheelSpeeds) {
        double maxSpeed = 0;
        for(double speed : wheelSpeeds) {
            maxSpeed = Math.max(maxSpeed, Math.abs(speed));
        }
        return maxSpeed;
    }

    /**
     * Represents the speeds of four Mecanum wheels.
     * <p>Provides getters, setters, and helper methods for scaling speeds.</p>
     */
    public static class MecanumWheelSpeeds {
        private final double[] speeds;

        /**
         * Constructs a MecanumWheelSpeeds object with given wheel speeds.
         *
         * @param speeds array of 4 wheel speeds: frontLeft, frontRight, backLeft, backRight
         */
        public MecanumWheelSpeeds(double[] speeds) {
            this.speeds = speeds;
        }

        public double getFrontLeft() {
            return speeds[0];
        }

        public double getFrontRight() {
            return speeds[1];
        }

        public double getBackLeft() {
            return speeds[2];
        }

        public double getBackRight() {
            return speeds[3];
        }

        public void setFrontLeft(double speed) {
            speeds[0] = speed;
        }

        public void setFrontRight(double speed) {
            speeds[1] = speed;
        }

        public void setBackLeft(double speed) {
            speeds[2] = speed;
        }

        public void setBackRight(double speed) {
            speeds[3] = speed;
        }

        /**
         * Returns the underlying wheel speeds array.
         *
         * @return array of wheel speeds
         */
        public double[] getSpeeds() {
            return speeds;
        }

        /**
         * Returns a new MecanumWheelSpeeds instance with all speeds multiplied by a scalar.
         *
         * @param x scalar multiplier
         * @return new MecanumWheelSpeeds with scaled speeds
         */
        public MecanumWheelSpeeds mul(double x) {
            double[] newSpeeds = new double[4];
            for (int i = 0; i < this.speeds.length; i++) {
                newSpeeds[i] = this.speeds[i] * x;
            }

            return new MecanumWheelSpeeds(newSpeeds);
        }
    }
}
