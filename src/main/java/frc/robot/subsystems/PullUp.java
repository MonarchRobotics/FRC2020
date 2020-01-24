/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.Climb;

import edu.wpi.first.wpilibj.DoubleSolenoid;

//Subsystem for climbing system
public class PullUp extends SubsystemBase {

  private DoubleSolenoid climbSolenoid;

  public PullUp(int openChannel, int closeChannel) {
      climbSolenoid = new DoubleSolenoid(openChannel, closeChannel);
  }

  // Extends the pneumatic ram
  public void extendClimb()
  {
      climbSolenoid.set(DoubleSolenoid.Value.kForward);
  }

  // Retracts the pneumatic ram
  public void retractClimb()
  {
      climbSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  // Finds the position of the [pneumatic]
  public DoubleSolenoid.Value getValue()
  {
    return climbSolenoid.get();
  }
  // @Override
  // public void initDefaultCommand(){
  //   setDefaultCommand(new ExampleCommand());
  // }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
