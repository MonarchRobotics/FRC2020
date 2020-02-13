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
    //the approximate speed we want the shooter to be at.
    //the target revolutions per second on the encoders.
    final double targetSpinSpeed = 100.0;
    final double error = 10.0;

    // VideoCapture camera;

    /**
     * Creates a new Shoot.
     *
     * @param turret The subsystem used by this command.
     */
    public Shoot(Turret turret) {
        this.turret = turret;

        //set the speed of each wheel to our guess speed
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(turret);
        motorControlLeft = new MotorControlPID(targetSpinSpeed,1.0,1.0,1.0,1.0);
        motorControlRight = new MotorControlPID(targetSpinSpeed,1.0,1.0,1.0,1.0);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        //If both triggers are pulled, motors run.
        if ((OI.joystick1.getTrigger() && OI.joystick2.getTrigger()) || OI.joystick1.getRawButton(10)){
            double leftSpeed = motorControlLeft.getSpeed(turret.getEncoderLeftRate());
            double rightSpeed = motorControlRight.getSpeed(turret.getEncoderRightRate());
            turret.spinMotors(leftSpeed,rightSpeed);

            // Waits until the encoders are moving at a certain speed to start spinning the feeder wheel.
            if(turret.getEncoderLeftRate()>targetSpinSpeed-error && turret.getEncoderRightRate()>targetSpinSpeed-error && turret.getEncoderRightRate()<targetSpinSpeed+error && turret.getEncoderLeftRate()<targetSpinSpeed+error) {
                turret.getInputWheelMotor().set(ControlMode.PercentOutput,0.5);
            }
            else{
                turret.getInputWheelMotor().set(ControlMode.PercentOutput,0.0);
            }
//
//            //once the encoders are moving at a certain speed, start to adjust them so that they move at targetSpinSpeed
//            if(turret.getEncoderLeftRate()>50 && turret.getEncoderRightRate()>50){
//                //calculate the difference in the targetSpinSpeed and the current encoder speed, and divide by 100.
//                double differenceLeft = (turret.getEncoderLeftRate() - targetSpinSpeed)/-100.0;
//                double differenceRight = (turret.getEncoderRightRate() - targetSpinSpeed)/-100.0;
//
//                //adjust the speed of each motor with the differences from above.
//                leftSpeed+=differenceLeft;
//                rightSpeed+=differenceRight;
//            }
//
//            turret.getWheelMotor().set(ControlMode.PercentOutput, -leftSpeed);
//            turret.getWheel2Motor().set(ControlMode.PercentOutput, rightSpeed);
            
        }
        else {
            turret.spinMotors(0.0,0.0);
            motorControlLeft.reset();
            motorControlRight.reset();

            turret.getInputWheelMotor().set(ControlMode.PercentOutput, 0.0);
        }
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