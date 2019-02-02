package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Elevator {
  private WPI_TalonSRX motor;
  private final double elevatorMaxVoltage = 10.0;

  public Elevator() {
    motor = new WPI_TalonSRX(11);
    motor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder);
    motor.configVoltageCompSaturation(elevatorMaxVoltage);
    motor.enableVoltageCompensation(true);
  }

  public void move(double value) {
    motor.set(value);
  }

  public void dashboard() {
    SmartDashboard.putNumber("/Subsystem/Elevator/position", motor.getSelectedSensorPosition());
    SmartDashboard.putNumber("/Subsystem/Elevator/rate", motor.getSelectedSensorVelocity());
  }
}
