/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.BallIntake;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

//Subsystem for ball intake system
public class BallSuck extends SubsystemBase {

  
  // Motor to intake balls into the chassis
  private TalonSRX Intake;
  private boolean intakeS;
  private TalonSRX Handle;
  private boolean handleS;

  public BallSuck(int intake, int handle) {

    Intake = new TalonSRX(intake);
    intakeS = false;
    Handle = new TalonSRX(handle);
    handleS = false;
  }

  public void turnOnIntake()
  {
    Intake.set(ControlMode.PercentOutput,1);
    intakeS = true;
  }
  public void turnOffIntake()
  {
    Intake.set(ControlMode.PercentOutput, 0);
    intakeS = false;
  }

  public boolean intakeStat()
  {
    return intakeS;
  }


  public void turnOnHandle()
  {
    Handle.set(ControlMode.PercentOutput,1);
    handleS = true;
  }
  public void turnOffHandle()
  {
    Handle.set(ControlMode.PercentOutput, 0);
    handleS = false;
  }

  public boolean handleStat()
  {
    return handleS;
  }
  
//   @Override
//   public void initDefaultCommand(){
//     setDefaultCommand(new BallIntake());
//   }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
