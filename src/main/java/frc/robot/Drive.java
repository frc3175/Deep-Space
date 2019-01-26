package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Drive {


    private WPI_VictorSPX leftDrive;
    private WPI_VictorSPX rightDrive;
    private WPI_TalonSRX leftDriveT;
    private WPI_TalonSRX rightDriveT;
    private DifferentialDrive FalconL;
    private DoubleSolenoid Pancake;
    


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

    // Encoders
    leftDriveT.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder);
    rightDriveT.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder);

    leftDriveT.getSelectedSensorPosition();
    rightDriveT.getSelectedSensorPosition();

    // Differential drive
    FalconL = new DifferentialDrive(leftDriveT, rightDriveT);
}

public void move(double leftSpeed, double rightSpeed) {

    FalconL.tankDrive(leftSpeed, rightSpeed);
}

public void gearShifter(boolean shifter) {
    if (shifter == true) {
        Pancake.set(DoubleSolenoid.Value.kForward);
    }
    else if (shifter == false) {
        Pancake.set(DoubleSolenoid.Value.kReverse);
    }
}
}   