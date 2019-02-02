/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  // Operator
  private XboxController driver;
  private XboxController operator;

  // Subsystems
  private Drive drive;
  private Elevator elevator;
  private Intake intake;
  private Auton auton;

  // Pneumatics
  private Compressor compressor;


  @Override
  public void robotInit() {
    // Subsystems
    drive = new Drive();
    elevator = new Elevator();
    intake = new Intake();
    auton = new Auton(drive);

    // Controllers
    driver = new XboxController(0);
    operator = new XboxController(1);

    // initialize pneumatics
    compressor = new Compressor(0);
    compressor.setClosedLoopControl(true);
  }

  @Override
  public void robotPeriodic() {
    super.robotPeriodic();

    elevator.dashboard();
  }

  @Override
  public void autonomousInit() {
    auton.init();
  }

  @Override
  public void autonomousPeriodic() {
    auton.periodic();
  }

  @Override
  public void teleopPeriodic() {

    // Drive
    // left Y, right X, right shoulder
    drive.move(driver.getRawAxis(1), driver.getRawAxis(4), driver.getRawButton(6));
    
    // Intake
    if (driver.getXButton()) {
      intake.move(0.6);
    } else if (driver.getYButton()) {
      intake.move(-0.6);
    } else {
      intake.move(0); 
    }
    
    // Elevator
    elevator.move(operator.getY());
  }
}
