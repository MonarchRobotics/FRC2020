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
  /*
  * Checks for all of the possible controls that interact with the intake and 
  * and the internal manipulation "the Roller coster"
  *
  * These are the 
    * If right button 2 is pressed both are on
    * If left button 2 is pressed only handling "Roller coaster" is on
    * If neither, neither are on
  *
  */
  @Override
  public void execute() {

    
    // if(OI.rightJoystick.getRawButton(3)){}
    int pov = OI.xboxController.getPOV();
    if (!OI.rightButton2.get() && !OI.leftButton2.get() && !OI.leftJoystick.getRawButton(3) && pov != 0 && pov != 270 && pov != 180)
    {
      ballSuck.turnOffIntake();
      ballSuck.turnOffHandle();
    }
    else if (OI.leftButton2.get() || pov == 270)
    {
      ballSuck.turnOnHandle();
    }
    else if (OI.rightButton2.get() || pov == 0)
    {
      // System.out.println("Button pressed");
      ballSuck.turnOnIntake();
      ballSuck.turnOnHandle();
    }
    else if(OI.leftJoystick.getRawButton(3) || pov == 180){
      ballSuck.reverseHandle();
      ballSuck.reverseIntake();
    }
    
  }

  /* @return the subsystem ballsuck*/
  public BallSuck getBallSuck()
  {
    return ballSuck;
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