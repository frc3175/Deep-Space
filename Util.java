package frc.robot;

public class Util {
    public static double deadband(double val, double threshold) {
        if (Math.abs(val) < threshold) {
            return 0;
        }
        return val;
    }
}