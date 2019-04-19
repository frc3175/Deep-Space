package frc.robot;

import java.lang.Math;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Drive {

  private XboxController driver;
  // limelight stuff
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  public boolean LimelightHasValidTarget = false;
  public double LimelightDrive = 0.0;
  public double LimelightSteer = 0.0;

  private WPI_TalonSRX leftDrive0;
  private WPI_TalonSRX rightDrive0;
  private WPI_TalonSRX leftDriveT;
  private WPI_TalonSRX rightDriveT;

  // motor controller groups
  private SpeedControllerGroup LeftMotors = new SpeedControllerGroup(leftDrive0, leftDriveT);
  private SpeedControllerGroup RightMotors = new SpeedControllerGroup(rightDrive0, rightDriveT);

  private DifferentialDrive drive;
  private DoubleSolenoid Pancake;

  // Subsystem in a Subsystem???? Jessica have mercy but this works
  private LimeLight limeLight;

  // anthony stuff below
  public ADXRS450_Gyro gyro;
  public static final double maxTurn = 60;
  private static final double P = 0.03175;
  private static double setAngle = 0.0;
  // public state
  public int gearshift;

  public Drive() {
    // subsystems
    limeLight = new LimeLight();

    // VictorSPX motor controllers
    leftDrive0 = new WPI_TalonSRX(13);
    rightDrive0 = new WPI_TalonSRX(14);
    // Talon motor Controllers
    leftDriveT = new WPI_TalonSRX(12);
    rightDriveT = new WPI_TalonSRX(10);

    // driver controller
    driver = new XboxController(0);

    Pancake = new DoubleSolenoid(0, 1);

    // Current limiting
    leftDriveT.configContinuousCurrentLimit(35);
    leftDriveT.configPeakCurrentDuration(250);
    leftDriveT.configPeakCurrentLimit(50);
    leftDriveT.configOpenloopRamp(.4);

    leftDrive0.configContinuousCurrentLimit(35);
    leftDrive0.configPeakCurrentDuration(250);
    leftDrive0.configPeakCurrentLimit(50);
    leftDrive0.configOpenloopRamp(.4);

    rightDriveT.configContinuousCurrentLimit(35);
    rightDriveT.configPeakCurrentDuration(250);
    rightDriveT.configPeakCurrentLimit(50);
    rightDriveT.configOpenloopRamp(.4);

    rightDrive0.configContinuousCurrentLimit(35);
    rightDrive0.configPeakCurrentDuration(250);
    rightDrive0.configPeakCurrentLimit(50);
    rightDrive0.configOpenloopRamp(.4);

    // gyro
    gyro = new ADXRS450_Gyro();
    // sets the encoders
    leftDriveT.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative);
    rightDriveT.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative);

    // Differential drive
    drive = new DifferentialDrive(LeftMotors, RightMotors);

    // Limelight shit
    double steer_Limelight = driver.getX(Hand.kRight);
    double drive_Limelight = -driver.getY(Hand.kLeft);
    boolean auto_Limelight = driver.getBButton();

    steer_Limelight *= 0.70;
    drive_Limelight *= 0.70;

    if (auto_Limelight) {
      if (LimelightHasValidTarget) {
        drive.curvatureDrive(LimelightDrive, LimelightSteer, false);
      } else {
        drive.curvatureDrive(0.0, 0.0, false);
      }
    } else {
      drive.curvatureDrive(drive_Limelight, steer_Limelight, false);
    }
  }

  public void reset() {
    leftDriveT.setSelectedSensorPosition(0);
    rightDriveT.setSelectedSensorPosition(0);
  }

  public void move(double linearSpeed, double curveSpeed, boolean quickT) {

    /*
     * if(curveSpeed != 0) { setAngle = gyro.getAngle(); } else { curveSpeed =
     * (setAngle - gyro.getAngle()) * P; }
     */
    drive.curvatureDrive(linearSpeed, curveSpeed, quickT);
  }

  public void gearShifter(boolean shifter) {
    if (shifter) {
      Pancake.set(DoubleSolenoid.Value.kForward);
      gearshift = 1;
    } else {
      Pancake.set(DoubleSolenoid.Value.kReverse);
      gearshift = 0;
    }
    SmartDashboard.putBoolean("Gearshifter", shifter);
  }

  public void calibrate() {
    this.gyro.calibrate();
  }

  public void limeLightTeleopInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  public void limeLightAutonInit() {
    m_autoSelected = m_chooser.getSelected();
  }
}
