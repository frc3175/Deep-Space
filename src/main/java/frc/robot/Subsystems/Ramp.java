package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;

public class Ramp {
    double startTime;
    // private DoubleSolenoid rampPiston;
    private Servo rampServo;

    public Ramp () {
        
        //rampPiston = new DoubleSolenoid(6,2);
        rampServo = new Servo(0);
    }

    public void servoRamp(boolean pressed) {
        if (pressed == true) {
            rampServo.set(.75);
            rampServo.setAngle(95);

        } 
    }
}
//Ramp Piston Function
    // }
    // public void pistonRamp(boolean pressed) {
    //     if (pressed == true) {
    //         rampPiston.set(DoubleSolenoid.Value.kForward);
    //         } else {
    //         rampPiston.set(DoubleSolenoid.Value.kReverse);
    //         }
    // }
    // //probably wont use this function
    // public void doAllRampStuff(boolean pressed) {
    //     if (pressed == true) {
    //         startTime = System.currentTimeMillis();
    //         rampServo.set(.75);
    //         rampServo.setAngle(95);
    //         if (startTime >= 3); {
    //             rampPiston.set(DoubleSolenoid.Value.kForward);
    //         }
    //     }
    // }

    // }