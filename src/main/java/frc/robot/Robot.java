/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.Auton;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.LevelTwo;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.Ramp;
import frc.robot.subsystems.Operator;
import frc.robot.subsystems.lib.*;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.UsbCamera;

public class Robot extends TimedRobot {

  // Operator
  public int operatorPort = 1;
  public Joystick operator;
  //driver
  private XboxController driver;
  public int driverPort = 0;

  // Subsystems
  private Drive drive;
  private Elevator elevator;
  private Intake intake;
  private Auton auton;
  private Manipulator manipulator;
  private Ramp ramp;
  private LevelTwo leveltwo;
  private LimeLight limeLight;
  private Operator operatorClass;

  //library
  private KvLib kvlib;

  // states
  private int state = 0;
  private int stateOn = 0;
  private int RumbleState = 0;

  // toggle
  boolean ButtonPressed;
  private boolean l2State = false;
  private boolean l1State = false;
  private boolean cargoShipState = false;
  private boolean hatchState = false;

  private Timer timer;
  private Timer timerOn;
  private Timer RumbleTimer;

  // Pneumatics
  private Compressor compressor;

  public void robotInit() {
    // Subsystems
    drive = new Drive();
    ramp = new Ramp();
    elevator = new Elevator();
    intake = new Intake();
    auton = new Auton(drive);
    manipulator = new Manipulator();
    leveltwo = new LevelTwo();
    limeLight = new LimeLight();
    operatorClass = new Operator();
    //library
    kvlib = new KvLib();
    // Controllers
    driver = new XboxController(driverPort);
    operator = new Joystick(operatorPort);
    // initializes the elevator
    elevator.elevatorInit();
    // gyro

    // timer
    timer = new Timer();
    timerOn = new Timer();
    RumbleTimer = new Timer();
    timer.start();
    timerOn.start();
    RumbleTimer.start();

    // Camera
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(160, 120);
    camera.setBrightness(10);
    camera1.setResolution(160, 120);
    camera1.setBrightness(10);

    // limeLight Camera Init
    drive.limeLightTeleopInit();

    // initialize pneumatics
    compressor = new Compressor(0);
    compressor.setClosedLoopControl(true);

    // initialize Gyro
    drive.calibrate();
  }

  @Override
  public void robotPeriodic() {
    // super.robotPeriodic();
    elevator.dashboard();
  }

  @Override
  public void autonomousInit() {
    auton.init();
    drive.gearShifter(false);
    drive.limeLightAutonInit();
  }

  @Override
  public void teleopInit() {
    drive.gearShifter(true);
    elevator.elevatorInit();
  }

  @Override
  public void autonomousPeriodic() {
    auton.periodic();
    teleopPeriodic();
  }

  @Override
  public void teleopPeriodic() {
    operatorClass.periodicMethods();

    // periodic limelight
    limeLight.limeLight_Tracking_Periodic();

    // left Y, right X, right shoulder
    double linearSpeed = -driver.getRawAxis(1);
    double curveSpeed = -driver.getRawAxis(4);
    // deadzone
    if (Math.abs(linearSpeed) < 0.2) {
      linearSpeed = 0;
    }
    if (Math.abs(curveSpeed) < 0.2) {
      curveSpeed = 0;
    }
    if (drive.gearshift == 0) {
      RumbleHighGear();
    }

    if (!hatchOff()) {

      drive.move(linearSpeed, curveSpeed, driver.getRawButton(6));
      if (driver.getRawButton(6) == true) {
        ButtonPressed = true;
      } else {
        ButtonPressed = false;
      }
      SmartDashboard.putNumber("CurveSpeed", curveSpeed);
      SmartDashboard.putNumber("LinearSpeed", linearSpeed);
      SmartDashboard.putBoolean("Quick Turn", ButtonPressed);
      // Intake
      if (operator.getRawButton(3)) {
        intake.set(-0.7);
      } else if (operator.getRawButton(4)) {
        intake.set(0.7);
      } else {
        intake.set(0);
      }
      // gearShifter
      if (driver.getXButton()) {
        drive.gearShifter(true);
      } else if (driver.getAButton()) {
        drive.gearShifter(false);
      }
      // Elevator deadzone
      double verticalSpeed = operator.getY();
      if (Math.abs(verticalSpeed) < 0.07) {
        verticalSpeed = 0;
      }
      // elevator
      if (!elevator.cargoShipPreset(operator.getRawButton(11)) && !elevator.levelOne(operator.getRawButton(9))
          && !elevator.levelTwo(operator.getRawButton(10)) && !elevator.grabHatch(operator.getRawButton(8))) {
        elevator.UpnDown(operator.getY());
      }
      // Hatch Release
      //temporarily commented out to test operatorClass
      //manipulator.secureHatchFunction(operator.getRawButton(1));
      // Manipulator (Its the piston thing that makes the intake up and down)
      manipulator.manipulatorUp(operator.getRawButton(5));
      manipulator.manipulatorDown(operator.getRawButton(6));

      
      // Elevator presets
      if (operator.getRawButton(10)) {
        l2State = true;
      }
      if (operator.getRawButton(11)) {
        cargoShipState = true;
      }
      if (operator.getRawButton(9)) {
        l1State = true;
      }
      if (operator.getRawButton(8)) {
        hatchState = true;
      }

      cargoShipState = elevator.cargoShipPreset(cargoShipState);
      elevator.resetPos(operator.getRawButton(12));
      l1State = elevator.levelOne(l1State);
      l2State = elevator.levelTwo(l2State);
      hatchState = elevator.grabHatch(hatchState);

      // gyro smartDash
      SmartDashboard.putNumber("Gyro Angle", drive.gyro.getAngle());
      SmartDashboard.putNumber("Joystick Value", operator.getY());
    }
  }

  public boolean hatchOff() {
    if (operator.getRawButton(7)) {

      if (state == 0) {
        manipulator.releasingHatch(true);
        if (timer.hasPeriodPassed(0.5)) {
          state++;
        }
      } else if (state == 1) {
        elevator.UpnDown(0.35);
        if (timer.hasPeriodPassed(0.5)) {
          state++;
          elevator.UpnDown(0);
          manipulator.releasingHatch(false);
        }
      } else if (state == 2) {
        drive.move(-0.7, 0, false);
        if (timer.hasPeriodPassed(0.4)) {
          state++;
          drive.move(0, 0, false);
        }
      }
    } else {
      timer.reset();
      state = 0;
      return false;
    }
    return true;
  }

  // public boolean hatchOn() {
  // if (operator.getRawButton(8)) {
  // if (stateOn == 0) {
  // if (timerOn.hasPeriodPassed(0.5)) {
  // stateOn++;
  // }
  // } else if (stateOn == 1) {
  // elevator.UpnDown(-0.8);
  // if (timerOn.hasPeriodPassed(0.8)) {
  // stateOn++;
  // elevator.UpnDown(0);
  // }
  // } else if (stateOn == 2) {
  // drive.move(-0.7, 0, false);
  // if (timerOn.hasPeriodPassed(1)) {
  // stateOn++;
  // drive.move(0, 0, false);
  // }
  // }
  // } else {
  // timerOn.reset();
  // stateOn = 0;
  // return false;
  // }
  // return true;
  // }

  public boolean RumbleHighGear() {
    if (operator.getRawButton(2)) {
      if (RumbleState == 0) {
        driver.setRumble(RumbleType.kRightRumble, 1);
        if (RumbleTimer.hasPeriodPassed(0.3)) {
          RumbleState++;
        }
      } else if (RumbleState == 1) {
        driver.setRumble(RumbleType.kRightRumble, 0);
        if (RumbleTimer.hasPeriodPassed(0.3)) {
          driver.setRumble(RumbleType.kRightRumble, 1);
          RumbleState++;
        }
      } else {
        RumbleTimer.reset();
        driver.setRumble(RumbleType.kRightRumble, 0);
        RumbleState = 0;
        return false;
      }
    } else {
      driver.setRumble(RumbleType.kRightRumble, 0);
    }
    return true;
  }
}
