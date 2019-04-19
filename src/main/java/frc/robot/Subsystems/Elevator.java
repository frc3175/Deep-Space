package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.*;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Elevator {
  private WPI_TalonSRX motor;
  private final double elevatorMaxVoltage = 10.0;
  private int state;

  public Elevator() {
    motor = new WPI_TalonSRX(11);
    motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    motor.configVoltageCompSaturation(elevatorMaxVoltage);
    motor.enableVoltageCompensation(true);
    motor.setInverted(false);

    motor.configContinuousCurrentLimit(25);
    motor.configPeakCurrentLimit(0);
    motor.enableCurrentLimit(true);

    // Forward limit switch resets sensor position
    if (motor.getSensorCollection().isFwdLimitSwitchClosed() == true) {
      motor.setSelectedSensorPosition(0);
    }
  }

  public void UpnDown(double value) {
    if (value > 0.1) {
      motor.set((-value * 0.95)); // DOWN
    } else if (value < -0.1) {
      motor.set(-(value * 0.95) + 0.08); // UP
    } else if (motor.getSensorCollection().isFwdLimitSwitchClosed() == true) {
      motor.set(0.08);
    } else {
      motor.set(0.08);
    }
  }

  public void dashboard() {
    SmartDashboard.putNumber("/Subsystem/Elevator/position", motor.getSelectedSensorPosition());
    SmartDashboard.putNumber("/Subsystem/Elevator/rate", motor.getSelectedSensorVelocity());
    SmartDashboard.putBoolean("Limit Switch", motor.getSensorCollection().isFwdLimitSwitchClosed());
  }

  public void elevatorInit() {
    motor.setSelectedSensorPosition(0);
  }

  public boolean cargoShipPreset(boolean pressed) {
    if (pressed) {
      if (motor.getSelectedSensorPosition() > -300000) {
        motor.set(1);
        return true;
      } else {
        motor.set(0.08);
        return false;
      }
    } else {
      return false;
    }
  }

  public void resetPos(boolean pressed) {
    if (pressed) {
      motor.setSelectedSensorPosition(0);
    }
  }

  public boolean levelOne(boolean pressed) {
    if (pressed) {
      if (motor.getSelectedSensorPosition() > -120000) {
        motor.set(1);
        return true;
      } else {
        motor.set(0.08);
        return false;
      }
    } else {
      return false;
    }
  }

  public boolean levelTwo(boolean pressed) {
    if (pressed) {
      if (motor.getSelectedSensorPosition() > -622000) {
        motor.set(1);
        return true;
      } else {
        motor.set(0.08);
        return false;
      }
    } else {
      return false;
    }
  }

  public boolean grabHatch(boolean pressed) {
    if (pressed) {
      if (motor.getSelectedSensorPosition() > -200000) {
        motor.set(1);
        return true;
      } else {
        motor.set(0.08);
        return false;
      }
    } else {
      return false;
    }
  }
}
