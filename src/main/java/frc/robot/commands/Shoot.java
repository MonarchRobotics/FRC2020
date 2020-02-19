/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.MotorControlPID;
import frc.robot.OI;
import frc.robot.subsystems.Turret;

import edu.wpi.first.wpilibj2.command.CommandBase;



/**
 * Command for turret subsystem.
 */
public class Shoot extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Turret turret;
    private MotorControlPID motorControlLeft;
    private MotorControlPID motorControlRight;
    //the target revolutions per second on the encoders.
    final double targetSpinSpeed = 24.0;
    final double error = 10.0;

    // VideoCapture camera;

    /**
     * Creates a new Shoot.
     *
     * @param turret The subsystem used by this command.
     * 
     * Creats the two MotorControlPID classes for both the left and right wheels of the ball shooting system
     * 
     */
    public Shoot(Turret turret) {
        this.turret = turret;

        //timer = new Timer();
        //set the speed of each wheel to our guess speed
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(turret);
        motorControlLeft = new MotorControlPID(targetSpinSpeed,1.0,1.0,0.05,0.001);
        motorControlRight = new MotorControlPID(targetSpinSpeed,1.0,1.0,0.05,0.001);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

        //timer.reset();

    }

    /**
     * When the left and right triggers on the joysticks are pressed (or button 10 on the right joystick)
     *      Turns the ballshooter wheels on to a certain amount
     *          Gets the speeds to set it to based on a ramp up to a certain RPM based on our MotorControlPID classes defined in the constructor
     * 
     */
    @Override
    public void execute() {
        //If both triggers are pulled, motors run.

        if ((OI.rightJoystick.getTrigger() && OI.leftJoystick.getTrigger()) || OI.rightJoystick.getRawButton(10)){
            double leftSpeed = motorControlLeft.getSpeed(turret.getEncoderLeftRate());
            // double rightSpeed = motorControlRight.getSpeed(turret.getEncoderRightRate());
            turret.spinMotors(leftSpeed,leftSpeed);
            // turret.spinMotors(0.60,0.60);
            SmartDashboard.putNumber("Left Speed", leftSpeed);
            SmartDashboard.putNumber("Left RPS", turret.getEncoderLeftRate());

            System.out.println("RPM:"+turret.getEncoderLeftRate());



            if(OI.rightJoystick.getRawButton(5)){
                turret.getInputWheelMotor().set(ControlMode.PercentOutput,1.0);
            }
            else {
                turret.getInputWheelMotor().set(ControlMode.PercentOutput,0.0); 
            }
        }
        else {
            // SmartDashboard.putNumber("Left Speed", 0);
            // SmartDashboard.putNumber("Left RPM", 0);
            turret.spinMotors(0.0,0.0);
            motorControlLeft.reset();
            motorControlRight.reset();

            turret.getInputWheelMotor().set(ControlMode.PercentOutput, 0.0);
            // turret.getInputWheelMotor().set(ControlMode.PercentOutput,1.0);
        }
    
    }

     /**
     * @return the motorControlLeft
     */
    public MotorControlPID getMotorControlLeft() {
        return motorControlLeft;
    }
    /**
     * @return the motorControlRight
     */
    public MotorControlPID getMotorControlRight() {
        return motorControlRight;
    }
    
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}