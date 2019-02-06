/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  // Operator
  private XboxController driver;
  private Joystick operator; 
  // Subsystems
  private Drive drive;
  private Elevator elevator;
  private Intake intake;
  private Auton auton;
  private Manipulator manipulator;

  // Pneumatics
  private Compressor compressor;


  @Override
  public void robotInit() {
    // Subsystems
    drive = new Drive();
    elevator = new Elevator();
    intake = new Intake();
    auton = new Auton(drive);
    manipulator = new Manipulator();
    // Controllers
    driver = new XboxController(0);
    operator = new Joystick(2);

    // initialize pneumaticss
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
    drive.move(-driver.getRawAxis(1), -driver.getRawAxis(4), driver.getRawButton(6));
    
    // Intake
    if (operator.getRawButton(3)) {
      intake.move(0.7);
    } else if (operator.getRawButton(4)) {
      intake.move(-0.7);
    } else {
      intake.move(0); 
    }

    //gearShifter
    if (driver.getXButton()) {
      drive.gearShifter(true);
    } else if (driver.getAButton()) {
      drive.gearShifter(false);
    }
    // Elevator
    elevator.move(operator.getY());

    //Hatch Release
    manipulator.releasingHatch(operator.getRawButton(6));

    //FloppyThing (Its the piston thing that makes the intake up and down)
    manipulator.flopThingUp(operator.getRawButton(5));
    manipulator.flopThingDown(operator.getRawButton(2));
  }
}
