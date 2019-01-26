// package frc.robot;

// import java.lang.System;
// import frc.robot.Robot;
// import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.Victor;
// import TalonSRX

// public class Auton  extends TimedRobot{

//     private Victor leftDrive;
//     private Victor rightDrive;
//     private TalonSRX leftDriveT;
//     private TalonSRX rightDriveT;

// public void autonomousPeriodic() {
//     int averageDistance = (leftDriveT.getSelectedSensorPosition() + rightDriveT.getSelectedSensorPosition()) / 2;
//     // forward 2
//     if (averageDistance <= 2048 && autonState == 0) {
//       FalconL.tankDrive(1, 1);
//     } else if (autonState == 0) {
//       FalconL.tankDrive(0, 0);
//       autonState++;
//       System.out.println(autonState);
//       // Backwards
//     }
//     if (averageDistance >= 0 && autonState == 1) {
//       FalconL.tankDrive(-1, -1);
//     } else if (autonState == 1) {
//       FalconL.tankDrive(0, 0);
//       autonState++;
//       System.out.println(autonState);
//     }
//     // right 1 rotation
//     if (leftDriveT.getSelectedSensorPosition() < 1024 && autonState == 2) {
//       FalconL.tankDrive(1, 0);
//     } else if (autonState == 2) {
//       FalconL.tankDrive(0, 0);
//       autonState++;
//       System.out.println(autonState);
//     }
//     // Forward 4 rotations
//     if (leftDriveT.getSelectedSensorPosition() >= 4096 && autonState == 3) {
//       FalconL.tankDrive(1, 1);
//     } else if (autonState == 3) {
//       FalconL.tankDrive(0, 0);
//       autonState++;
//       System.out.println(autonState);
//     }
//   }

//   {
//     // Sets the sensor positions back to zero
//     leftDriveT.setSelectedSensorPosition(0);
//     rightDriveT.setSelectedSensorPosition(0);
//   }
//   // Declare the variable again cause Idk wut im doing
//   int averageDistance = (leftDriveT.getSelectedSensorPosition() + rightDriveT.getSelectedSensorPosition()) / 2;
//   {
//     // Forward 2 rotations
//     if (averageDistance <= 2048 && autonState == 4) {
//       FalconL.tankDrive(1, 1);
//     } else if (autonState == 4) {
//       FalconL.tankDrive(0, 0);
//       autonState++;
//       System.out.println(autonState);
//     }
//   }
//   {
//     // left 4 rotatations
//     if (averageDistance <= 2048 && autonState == 5) {
//       FalconL.tankDrive(0, 1);
//     } else if (autonState == 5)
//       FalconL.tankDrive(0, 0);
//     autonState++;
//     System.out.println(autonState);
//   }
//   {
//     if (averageDistance <= 10240 && autonState == 6) {
//       FalconL.tankDrive(1, 1);
//     }
//     else if(autonState == 6)
//       FalconL.tankDrive(0, 0);
//       autonState++;
//       System.out.println(autonState);
//   }