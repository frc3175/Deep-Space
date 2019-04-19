package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Manipulator {

    private Solenoid hatchPistons;
    private DoubleSolenoid floppyTin;
    private DoubleSolenoid secureHatch;
    boolean ButtonPressed;

    public Manipulator() {
        hatchPistons = new Solenoid(5);
        floppyTin = new DoubleSolenoid(3, 4);
        secureHatch = new DoubleSolenoid(6, 7);
    }

    public void releasingHatch(boolean pressed) {
        if (pressed == true) {
            hatchPistons.set(true);
        } else {
            hatchPistons.set(false);
        }
    }

    public void manipulatorUp(boolean pressed) {
        if (pressed) {
            ButtonPressed = true;
            floppyTin.set(DoubleSolenoid.Value.kForward);
            SmartDashboard.putBoolean("Manipulator", ButtonPressed);
        }
    }

    public void manipulatorDown(boolean pressed) {
        if (pressed) {
            ButtonPressed = false;
            floppyTin.set(DoubleSolenoid.Value.kReverse);
            SmartDashboard.putBoolean("Manipulator", ButtonPressed);

        }
    }

    public void secureHatchFunction(boolean pressed) {
        if (pressed) {
            secureHatch.set(DoubleSolenoid.Value.kReverse);
        } else {
            secureHatch.set(DoubleSolenoid.Value.kForward);
        }
    }
}
