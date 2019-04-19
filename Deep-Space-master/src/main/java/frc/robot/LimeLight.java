package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight {

    // objects n stuff

    //subsystems
    private Drive drive;

    public void limeLight_Tracking_Periodic() {

        //subsystems
        drive = new Drive();

        // tuning PID
        final double STEER_K = 0.03; // how hard to turn toward the target
        final double DRIVE_K = 0.26; // how hard to drive fwd toward the target
        final double DESIRED_TARGET_AREA = 13.0; // Area of the target when the robot reaches the wall
        final double MAX_DRIVE = 0.7; // Simple speed limit so we don't drive too fast

        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tx = table.getEntry("tx");
        NetworkTableEntry ty = table.getEntry("ty");
        NetworkTableEntry ta = table.getEntry("ta");

        // read values periodically
        double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double area = ta.getDouble(0.0);

        // post to smart dashboard periodically
        SmartDashboard.putNumber("LimelightX", x);
        SmartDashboard.putNumber("LimelightY", y);
        SmartDashboard.putNumber("LimelightArea", area);

        if (tv < 1.0) {
            drive.LimelightHasValidTarget = false;
            drive.LimelightDrive = 0.0;
            drive.LimelightSteer = 0.0;
            return;
        }

        drive.LimelightHasValidTarget = true;

        // Start with proportional steering
        double steer_cmd = x * STEER_K;
        drive.LimelightSteer = steer_cmd;

        // try to drive forward until the target area reaches our desired area
        double drive_cmd = (DESIRED_TARGET_AREA - area) * DRIVE_K;

        // don't let the robot drive too fast into the goal
        if (drive_cmd > MAX_DRIVE) {
            drive_cmd = MAX_DRIVE;
        }
        drive.LimelightDrive = drive_cmd;
    }
}
