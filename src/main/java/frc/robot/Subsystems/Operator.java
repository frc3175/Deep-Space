package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Robot;
import frc.robot.subsystems.lib.KvLib;

public class Operator {

    private Robot robot;
    private KvLib kvLib;
    private Drive drive;
    private Manipulator manipulator;

    public Operator() {
        robot = new Robot();
        kvLib = new KvLib();
        drive = new Drive();
        manipulator = new Manipulator();


    }
    public void periodicMethods(){
        kvLib.simplePiston(robot.operator.getRawButton(1), manipulator.secureHatch);
    }
}