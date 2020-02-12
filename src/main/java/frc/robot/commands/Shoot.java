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
import frc.robot.OI;
import frc.robot.subsystems.Turret;

import edu.wpi.first.wpilibj2.command.CommandBase;



/**
 * Command for turret subsystem.
 */
public class Shoot extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Turret turret;

    Timer inputTimer;

    boolean timerStart;
    boolean timerDone;
    final double initialSpeed = 0.43;
    final double targetSpinSpeed = 100.0;
    double leftSpeed;
    double rightSpeed;

    // VideoCapture camera;

    /**
     * Creates a new Shoot.
     *
     * @param turret The subsystem used by this command.
     */
    public Shoot(Turret turret) {
        this.turret = turret;

        inputTimer = new Timer();
        inputTimer.reset();

        leftSpeed = initialSpeed;
        rightSpeed = initialSpeed;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(turret);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        inputTimer.reset();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        //If both triggers are pulled, motors run.
        if ((OI.joystick1.getTrigger() && OI.joystick2.getTrigger()) || OI.joystick1.getRawButton(10)){

            // Waits a moment for shooter to spin up
            if(turret.getEncoderLeftRate()>targetSpinSpeed-10 && turret.getEncoderRightRate()>targetSpinSpeed-10 && turret.getEncoderRightRate()<targetSpinSpeed+10 && turret.getEncoderLeftRate()<targetSpinSpeed+10) {
                turret.getInputWheelMotor().set(ControlMode.PercentOutput,0.5);
            }
            else{
                turret.getInputWheelMotor().set(ControlMode.PercentOutput,0.0);
            }

            if(turret.getEncoderLeftRate()>50 && turret.getEncoderRightRate()>50){
                double differenceLeft = (turret.getEncoderLeftRate() - targetSpinSpeed)/-100.0;
                double differenceRight = (turret.getEncoderRightRate() - targetSpinSpeed)/-100.0;

                leftSpeed+=differenceLeft;
                rightSpeed+=differenceRight;


            }

            turret.getWheelMotor().set(ControlMode.PercentOutput, -leftSpeed);
            turret.getWheel2Motor().set(ControlMode.PercentOutput, rightSpeed);
            
        }
        else {
            turret.getWheelMotor().set(ControlMode.PercentOutput, 0.0);
            turret.getWheel2Motor().set(ControlMode.PercentOutput, 0.0);
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