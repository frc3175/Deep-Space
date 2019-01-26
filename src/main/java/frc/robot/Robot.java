/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;



import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;
import java.lang.System;
import frc.robot.Drive;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */

public class Robot extends TimedRobot {

  // robot
  private XboxController Driver;
  private XboxController operator;


  private Drive drive;
  private int autonState;

  int orientation = 1;

  // Pneumatics
  private Compressor compressor;

  // runtime timer
  Timer runTime = new Timer();


  @Override
  public void robotInit() {


    drive = new Drive();
    // Controllers
    Driver = new XboxController(0);
    operator = new XboxController(1);

    // initialize pneumatics
   
    compressor = new Compressor(0);
    compressor.setClosedLoopControl(true);

  }

  public void autonomousInit() {

    autonState = 0;
   // leftDriveT.setSelectedSensorPosition(0);
   // rightDriveT.setSelectedSensorPosition(0);
    // runTime.reset();
    // runTime.start();

  }

  // public void autonomousPeriodic() {
  //   int averageDistance = (leftDriveT.getSelectedSensorPosition() + rightDriveT.getSelectedSensorPosition()) / 2;
  //   // forward 2
  //   if (averageDistance <= 2048 && autonState == 0) {
  //     FalconL.tankDrive(1, 1);
  //   } else if (autonState == 0) {
  //     FalconL.tankDrive(0, 0);
  //     autonState++;
  //     System.out.println(autonState);
  //     // Backwards
  //   }
  //   if (averageDistance >= 0 && autonState == 1) {
  //     FalconL.tankDrive(-1, -1);
  //   } else if (autonState == 1) {
  //     FalconL.tankDrive(0, 0);
  //     autonState++;
  //     System.out.println(autonState);
  //   }
  //   // right 1 rotation
  //   if (leftDriveT.getSelectedSensorPosition() < 1024 && autonState == 2) {
  //     FalconL.tankDrive(1, 0);
  //   } else if (autonState == 2) {
  //     FalconL.tankDrive(0, 0);
  //     autonState++;
  //     System.out.println(autonState);
  //   }
  //   // Forward 4 rotations
  //   if (leftDriveT.getSelectedSensorPosition() >= 4096 && autonState == 3) {
  //     FalconL.tankDrive(1, 1);
  //   } else if (autonState == 3) {
  //     FalconL.tankDrive(0, 0);
  //     autonState++;
  //     System.out.println(autonState);
  //   }
  // }

  // {
  //   // Sets the sensor positions back to zero
  //   leftDriveT.setSelectedSensorPosition(0);
  //   rightDriveT.setSelectedSensorPosition(0);
  // }
  // // Declare the variable again cause Idk wut im doing
  // int averageDistance = (leftDriveT.getSelectedSensorPosition() + rightDriveT.getSelectedSensorPosition()) / 2;
  // {
  //   // Forward 2 rotations
  //   if (averageDistance <= 2048 && autonState == 4) {
  //     FalconL.tankDrive(1, 1);
  //   } else if (autonState == 4) {
  //     FalconL.tankDrive(0, 0);
  //     autonState++;
  //     System.out.println(autonState);
  //   }
  // }
  // {
  //   // left 4 rotatations
  //   if (averageDistance <= 2048 && autonState == 5) {
  //     FalconL.tankDrive(0, 1);
  //   } else if (autonState == 5)
  //     FalconL.tankDrive(0, 0);
  //   autonState++;
  //   System.out.println(autonState);
  // }
  // {
  //   if (averageDistance <= 10240 && autonState == 6) {
  //     FalconL.tankDrive(1, 1);
  //   }
  //   else if(autonState == 6)
  //     FalconL.tankDrive(0, 0);
  //     autonState++;
  //     System.out.println(autonState);
  // }

  @Override
  public void teleopPeriodic() {

    if (Driver.getAButton()) {
      orientation *= -1;
    }
    drive.move(orientation * Driver.getRawAxis(1) * -1, orientation * Driver.getRawAxis(5) * -1);

    if (Driver.getXButton()) {
      drive.gearShifter(true);
    }
    else if (Driver.getYButton())
    drive.gearShifter(false);
    }
  }
