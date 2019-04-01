package frc.robot;

import java.lang.Math;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Drive {

  private WPI_VictorSPX leftDrive;
  private WPI_VictorSPX rightDrive;
  private WPI_TalonSRX leftDriveT;
  private WPI_TalonSRX rightDriveT;
  private DifferentialDrive drive;
  private DoubleSolenoid Pancake;
  
  //anthony stuff below
  public ADXRS450_Gyro gyro;
  public static final double maxTurn = 60;
  private static final double P = 0.03175;
  private static double setAngle = 0.0;
  //public state
  public int gearshift;

  
  public Drive() {
    // VictorSPX motor controllers
    leftDrive = new WPI_VictorSPX(33);
    rightDrive = new WPI_VictorSPX(30);
    // Talon motor Controllers
    leftDriveT = new WPI_TalonSRX(12);
    rightDriveT = new WPI_TalonSRX(10);

    Pancake = new DoubleSolenoid(0, 1);

    leftDriveT.configContinuousCurrentLimit(35);
    leftDriveT.configPeakCurrentDuration(500);
    leftDriveT.configPeakCurrentLimit(40);

    
    rightDriveT.configContinuousCurrentLimit(35);
    rightDriveT.configPeakCurrentDuration(500);
    rightDriveT.configPeakCurrentLimit(40);

    leftDrive.follow(leftDriveT);
    rightDrive.follow(rightDriveT);

   // gyro
    gyro = new ADXRS450_Gyro();
    // sets the encoders
    leftDriveT.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative);
    rightDriveT.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative);

    // Differential drive
    drive = new DifferentialDrive(leftDriveT, rightDriveT);

  }

   public void reset() {
    leftDriveT.setSelectedSensorPosition(0);
    rightDriveT.setSelectedSensorPosition(0);
  }

  public void move(double linearSpeed, double curveSpeed, boolean quickT) {
    
    /*if(curveSpeed != 0) {
      setAngle = gyro.getAngle();
    }
    else {
      curveSpeed = (setAngle - gyro.getAngle()) * P;
    }*/
    drive.curvatureDrive(linearSpeed, curveSpeed, quickT);
  }

  public void gearShifter(boolean shifter) {
    if (shifter == true) {
      Pancake.set(DoubleSolenoid.Value.kForward);
      gearshift = 1;
    } else if (shifter == false) {
      Pancake.set(DoubleSolenoid.Value.kReverse);
      gearshift = 0;
    }
    SmartDashboard.putBoolean("Gearshifter", shifter);
  }
public void calibrate() {
  this.gyro.calibrate();
}
}