package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class LevelTwo {
    //Pneumatics
    private DoubleSolenoid firstStage;
    private DoubleSolenoid secondStage;


    public LevelTwo() {
        // firstStage = new DoubleSolenoid(8, 9);
        // secondStage = new DoubleSolenoid(10, 11);
    }
    public void climbFirst(boolean pressed) {
        if(pressed == true) {
            firstStage.set(DoubleSolenoid.Value.kForward);
        } else {
            firstStage.set(DoubleSolenoid.Value.kReverse);

        }
    }
    public void climbSecond(boolean pressed) {
        if(pressed == true) {
            secondStage.set(DoubleSolenoid.Value.kForward);
        } else {
            secondStage.set(DoubleSolenoid.Value.kReverse);
        }

}
}

