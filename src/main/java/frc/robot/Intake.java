package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Intake {
  private WPI_VictorSPX motor;
  private final double intakeMaxVoltage = 10.0;

  public Intake() {
    motor = new WPI_VictorSPX(31);
    // motor.configVoltageCompSaturation(intakeMaxVoltage);
    // motor.enableVoltageCompensation(true);
  }

  public void move(double value) {
    motor.set(value);
  }
}
