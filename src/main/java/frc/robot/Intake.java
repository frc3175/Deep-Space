package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake {
  private WPI_TalonSRX motor;
  private final double intakeMaxVoltage = 10.0;
  private boolean holdingBallState;
  public Intake() {
    motor = new WPI_TalonSRX(31);
    // motor.configVoltageCompSaturation(intakeMaxVoltage);
    // motor.enableVoltageCompensation(true);
  }

  public void set(double value) {
    if (holdingBallState == true && motor.getSelectedSensorPosition() < -10){
      motor.set(0.3);
    } else {
      motor.set(value);
    }
    }
  
  public void toggleBallHolding(boolean pressed) {
    if (pressed == true) {
      holdingBallState = !holdingBallState;
      motor.setSelectedSensorPosition(0);
    }
  }
}
