/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


import frc.robot.subsystems.BallSuck;


import frc.robot.OI;


import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;


/**
 * Ball intake system
 */
public class BallIntake extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final BallSuck ballSuck;
  
  


  /**
   * @param subsystem The subsystem used by this command.
   */
  public BallIntake(BallSuck subsystem) {
    ballSuck = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Toggle for intake motor
    if (OI.xboxController.getBumperPressed(Hand.kRight))
    {
      if (ballSuck.intakeStat())
      {
        ballSuck.turnOffIntake();
      }
      else
      {
        ballSuck.turnOnIntake();
      }
    }

    // Toggle for handle motor
    if (OI.xboxController.getBumperPressed(Hand.kLeft))
    {
      if (ballSuck.handleStat())
      {
        ballSuck.turnOffHandle();
      }
      else
      {
        ballSuck.turnOnHandle();
      }
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