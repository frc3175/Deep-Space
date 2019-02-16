package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
public class Manipulator {

    private Solenoid hatchPistons;
    private DoubleSolenoid floppyTin;

    public Manipulator() {
        hatchPistons = new Solenoid(5);
        floppyTin = new DoubleSolenoid(3, 4);
    }

    public void releasingHatch(boolean pressed) {
        if (pressed == true) {
            hatchPistons.set(true);
        } else {
            hatchPistons.set(false);
            }
    }
    
    public void flopThingUp(boolean pressed) {
        if (pressed == true) {
            floppyTin.set(DoubleSolenoid.Value.kForward);
        }
    }

    public void flopThingDown(boolean pressed) {
        if (pressed == true) {
            floppyTin.set(DoubleSolenoid.Value.kReverse);
        }
    }
}
