package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import edu.wpi.first.wpilibj.Joystick;

//TODO: Figure out what we're putting in Robot, RobotContainer, and OI
public class OI {

    public static XboxController xboxController;
    // Joystick1 is right
    // Joystick2 is left
    public static Joystick joystick1, joystick2;

    public static JoystickButton rightButton2;

    public static JoystickButton leftButton2;

    // joystick 1 buttons
    // public static JoystickButton button1;

    public OI(){
        xboxController = new XboxController(0);
        joystick1 = new Joystick(1);
        joystick2 = new Joystick(2);
        

        //Right buttons
        rightButton2 = new JoystickButton(joystick1, 2);

        // Left buttons
        leftButton2 = new JoystickButton(joystick2, 2);
        // button1 = new JoystickButton(joystick1, )
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
