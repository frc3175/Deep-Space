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
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.UsbCamera;

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
  private Ramp ramp;



  // Pneumatics
  private Compressor compressor;


  @Override
  public void robotInit() {
    // Subsystems
    drive = new Drive();
    ramp = new Ramp();
    elevator = new Elevator();
    intake = new Intake();
    auton = new Auton(drive);
    manipulator = new Manipulator();
    // Controllers
    driver = new XboxController(0);
    operator = new Joystick(2);
    //initializes the elevator
    elevator.elevatorInit();
    //gyro

    //Camera
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(160, 120);
    camera.setBrightness(5);
    camera1.setResolution(160, 120);
    camera1.setBrightness(2);
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
    double linearSpeed = -driver.getRawAxis(1);
    double curveSpeed = -driver.getRawAxis(4);
    if (Math.abs(linearSpeed) < 0.2) {
      linearSpeed = 0;
    }
    if (Math.abs(curveSpeed) < 0.2) {
      curveSpeed = 0;
    }
    drive.move(linearSpeed, curveSpeed, driver.getRawButton(6));
    // Intake
    if (operator.getRawButton(3)) {
      intake.set(0.7);
    } else if (operator.getRawButton(4)) {
      intake.set(-0.7);
    } else {
      intake.set(0); 
    }
    //gearShifter
    if (driver.getXButton()) {
      drive.gearShifter(true);
    } else if (driver.getAButton()) {
      drive.gearShifter(false);
    }
    // Elevator
    elevator.UpnDown(operator.getY());
    //Hatch Release
    manipulator.releasingHatch(operator.getRawButton(1));
    //FloppyThing (Its the piston thing that makes the intake up and down)
    manipulator.flopThingUp(operator.getRawButton(5));
    manipulator.flopThingDown(operator.getRawButton(6));
    //Makes the ramp go up and down
    ramp.servoRamp(operator.getRawButton(7));
    ramp.pistonRamp(operator.getRawButton(8));
    //ball holding
    intake.toggleBallHolding(operator.getRawButtonPressed(11));
  }
}
