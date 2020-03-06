package frc.robot;

import frc.robot.commands.auto.SpinToPort;
import frc.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.XboxController;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Controller and button input.
 */
public class OI {

    public static XboxController xboxController;

    // rightJoystick is right
    // leftJoystick is left
    public static Joystick rightJoystick, leftJoystick;

    // Buttons for the RIGHT joystick
    public static JoystickButton rightButton2; //ball intake
    public static JoystickButton rightButton8, rightButton9;

    // Buttons for the LEFT joystick
    public static JoystickButton leftButton2; //ball intake
    public static JoystickButton leftButton7; //turn to a certain degree
    public static JoystickButton leftButton6; // Turn to the port


    public OI(Drivetrain drivetrain){
        // Xbox controller
        xboxController = new XboxController(0);

        // Right joystick
        rightJoystick = new Joystick(1);
        // Left joystick
        leftJoystick = new Joystick(2);
        

        //Right buttons
        rightButton2 = new JoystickButton(rightJoystick, 2);

        // Left buttons
        leftButton2 = new JoystickButton(leftJoystick, 2);
        // button1 = new JoystickButton(rightJoystick, )

        // Activates the auto turn
        leftButton6 = new JoystickButton(leftJoystick, 6);
        leftButton6.whenPressed(new SpinToPort(drivetrain));

        // Test turn to a certain attitude
        leftButton7 = new JoystickButton(leftJoystick, 7);

        rightButton8 = new JoystickButton(rightJoystick,8);
        rightButton9 = new JoystickButton(rightJoystick,9);

    }

    /**
     *  Creats a dead zone for the joysticks/triggers that must be
    called and passed the joystick values.
     */
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
