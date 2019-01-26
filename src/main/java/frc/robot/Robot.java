/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.*;
import java.lang.System;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */

public class Robot extends TimedRobot {

  // robot
  private DifferentialDrive FalconL;
  private WPI_VictorSPX leftDrive;
  private Victor elevator;
  private WPI_VictorSPX rightDrive;
  private WPI_TalonSRX leftDriveT;
  private WPI_TalonSRX rightDriveT;
  private XboxController Driver;
  private XboxController operator;

  private int autonState;

  int orientation = 1;

  // Pneumatics
  private DoubleSolenoid Pancake;
  private Compressor compressor;

  // runtime timer
  Timer runTime = new Timer();

  @Override
  public void robotInit() {
    // Victor motor controllers
    leftDrive = new WPI_VictorSPX(33);
    rightDrive = new WPI_VictorSPX(30);
    elevator = new Victor(3);
    // Talon motor Controllers
    leftDriveT = new WPI_TalonSRX(0);
    rightDriveT = new WPI_TalonSRX(1);

    leftDrive.follow(leftDriveT);
    rightDrive.follow(rightDriveT);
    
    // Encoders
    leftDriveT.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder);
    rightDriveT.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder);

    leftDriveT.getSelectedSensorPosition();
    rightDriveT.getSelectedSensorPosition();

    // Differential drive
    FalconL = new DifferentialDrive(leftDrive, rightDrive);

    // Controllers
    Driver = new XboxController(0);
    operator = new XboxController(1);

    // initialize pneumatics
    Pancake = new DoubleSolenoid(0, 1);
    compressor = new Compressor(0);
    compressor.setClosedLoopControl(true);

  }

  public void autonomousInit() {

    autonState = 0;
    leftDriveT.setSelectedSensorPosition(0);
    rightDriveT.setSelectedSensorPosition(0);
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
    FalconL.tankDrive(orientation * Driver.getRawAxis(1), orientation * Driver.getRawAxis(5) * 0.9);

    if (operator.getAButton()){
      elevator.set(operator.getY() * .5);
    } else{
      elevator.set(0);
    }
    gearShifter();
  }

  private void gearShifter() {

    if (Driver.getXButton()) {

      PancakeL.set(DoubleSolenoid.Value.kForward);
      PancakeR.set(DoubleSolenoid.Value.kForward);
    } else if (Driver.getYButton()) {

      PancakeL.set(DoubleSolenoid.Value.kReverse);
      PancakeR.set(DoubleSolenoid.Value.kReverse);
    }
  }
}