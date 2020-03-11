/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.OI;
import frc.robot.subsystems.PullUp;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj2.command.CommandBase;


/**
 * The Pneumatic climbing system
 */
public class Climb extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final PullUp pullUp;
  
  private Timer time;


  /**
   * @param subsystem The subsystem used by this command.
   */
  public Climb(PullUp subsystem) {
    pullUp = subsystem;
    time = new Timer();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    time.reset();
    time.start();
  }

  /** 
   * This toggles the climbing system (pnumatic ram climbing system)
   * 
   */
  @Override
  public void execute() {

    // if (time.getMatchTime() <= 30)
    // {
      if (OI.leftJoystick.getRawButtonPressed(11) || OI.xboxController.getStartButtonPressed()) {
        System.out.println("Pressing button");
        if (pullUp.getValue() != DoubleSolenoid.Value.kForward) {
          pullUp.extendClimb();
        }
        else {
          pullUp.retractClimb();
        }
      }
    // }
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