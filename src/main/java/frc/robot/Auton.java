package frc.robot;

import edu.wpi.first.wpilibj.Timer;

public class Auton {
  private Drive drive;

  private int autonState;
  // runtime timer
  Timer runTime = new Timer();

  public Auton(Drive inDrive) {
    drive = inDrive;
  }

  public void init() {
    autonState = 0;
    runTime.reset();
    runTime.start();
    drive.reset();
  }

  public void periodic() {
    // int averageDistance = (leftDriveT.getSelectedSensorPosition() + rightDriveT.getSelectedSensorPosition()) / 2;
    // // forward 2
    // if (averageDistance <= 1024 && autonState == 0) {
    //   FalconL.tankDrive(1, 1);
    // } else if (autonState == 0) {
    //   FalconL.tankDrive(0, 0);
    //   autonState++;
    //   System.out.println(autonState);
    // }
  }
}
