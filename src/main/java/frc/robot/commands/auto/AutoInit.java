/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;

import frc.robot.OI;
import frc.robot.commands.BallIntake;
import frc.robot.subsystems.BallSuck;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Solenoid;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import edu.wpi.first.wpilibj.Timer;


/**
 * Gets everything that needs to outside of the frame perimiter
 */
public class AutoInit extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final BallSuck subsystem;
    private final Drivetrain drivetrain;

    private boolean activated;
    private Timer timer;

    /**
     * @param subsystem The BallSuck subsystem {@link BallSuck} so that we can extend the ballSuck outside of frame perimeter
     */
    public AutoInit(BallSuck subsystem, Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        this.subsystem = subsystem;
        timer = new Timer();

        // value to check if it has been activated so it will not activate same systems more than once
        activated = false;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        timer.start();
        // drivetrain.getGyro().calibrate();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        subsystem.intakeBackwards();
        // if (!activated)
        // {
            // Activate the release for the ball intake system
        //     subsystem.activateRelease();
        //     activated = true;
        // }
    }

    // Called once the command ends or is interrupted, sets motors to stop moving
    @Override
    public void end(boolean interrupted) {
        subsystem.turnOffIntake();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        //returns false while the system hasn't activated fully yet
        return timer.get()>0.2;
    }
}