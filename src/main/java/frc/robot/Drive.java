package frc.robot;

import java.lang.Math;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.analog.adis16448.frc.ADIS16448_IMU;

public class Drive {

  private WPI_VictorSPX leftDrive;
  private WPI_VictorSPX rightDrive;
  private WPI_TalonSRX leftDriveT;
  private WPI_TalonSRX rightDriveT;
  private DifferentialDrive drive;
  private DoubleSolenoid Pancake;
  
  //anthony stuff below
  private ADIS16448_IMU gyro;
  public static final double maxTurn = 60;
  //anthony stuff above


  public Drive() {
    // VictorSPX motor controllers
    leftDrive = new WPI_VictorSPX(33);
    rightDrive = new WPI_VictorSPX(30);
    // Talon motor Controllers
    leftDriveT = new WPI_TalonSRX(12);
    rightDriveT = new WPI_TalonSRX(10);

    Pancake = new DoubleSolenoid(0, 1);

    leftDrive.follow(leftDriveT);
    rightDrive.follow(rightDriveT);

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


  /*
   * This is Anthony's miserable attempt to try and program curvature while Ian is
   * gone, comment out if anthony is bad. 2/1/2019
   */
  public void move(double linearSpeed, double curveSpeed, boolean quickT) {
    // if (driver.getAButton()) {
    //   orientation *= -1;
    // }

    //Below is Ian's code
    // drive.move(orientation * Driver.getRawAxis(5) * -1, orientation * Driver.getRawAxis(1) * -1);
    // double wantTurn = Driver.getRawAxis(4);
    // double rate = gyro.getRateZ();
    // if (wantTurn*maxTurn < rate && (wantTurn != 1 || wantTurn != -1) && (Math.abs(rate/maxTurn - wantTurn) > 0.05)) {
    //   wantTurn+=0.05;
    // }
    // else if (wantTurn*maxTurn > rate && (wantTurn != 1 || wantTurn != -1) && (Math.abs(rate/maxTurn - wantTurn) > 0.05)) {
    //   wantTurn-=0.05;
    // }

    /*
    double targetRatePercentage = drive.getRawAxis(4);
    double targetRate = targetRatePercentage * maxRate
    double currentRate = gyro.getRateZ();
    double error = currentRate - targetRate;

    // double outputRate = error * kP; // Investigate PID loop
    // double outputRatePercentage = outputRate / maxRate;

    double outputRatePercentage = targetRatePercentage;
    if (Math.abs(outputRatePercantage) < 1 && Math.abs(error) > 0.05) {
      outputRatePercentage += error < 0 ? 0.5 : -0.5;
    }

    final double quickTurnThreshold = 0.2;
    drive.move(driver.getRawAxis(1), outputRatePercentage, driver.getRawAxis(3) > quickTurnThreshold);
    */

    // drive.move(Driver.getRawAxis(1), wantTurn, 0.2 <= Driver.getRawAxis(3));
    // Anthony code above


    drive.curvatureDrive(linearSpeed, curveSpeed, quickT);
  }

  public void gearShifter(boolean shifter) {
    if (shifter == true) {
      Pancake.set(DoubleSolenoid.Value.kForward);
    } else if (shifter == false) {
      Pancake.set(DoubleSolenoid.Value.kReverse);
    }
  }

}