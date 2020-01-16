package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;


public class OI {

    public static XboxController xboxController;

    public static Joystick joystick1, joystick2;

    public OI(){
        xboxController = new XboxController(0);
        joystick1 = new Joystick(1);
        joystick2 = new Joystick(2);
    }

    public static double deadZone (double val, double deadZone){
        if (Math.abs(val) > deadZone){
            if (val > 0){
                return (val - deadZone) / (1 - deadZone);
            } else {
                return -(-val - deadZone) / (1 - deadZone);
            }
        }
        return 0;
    }
}
