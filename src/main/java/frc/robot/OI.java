package frc.robot;

import edu.wpi.first.wpilibj.XboxController;


public class OI {

    public static XboxController xboxController;

    public OI(){
        xboxController = new XboxController(0);
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
