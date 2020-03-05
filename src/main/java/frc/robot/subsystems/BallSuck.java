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
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Solenoid;


/**
 * Subsystem for ball intake system
 */
public class BallSuck extends SubsystemBase {

  
  // Motor to intake balls into the chassis
  private Talon intake;
  private boolean intakeS;
  // Motor for handling the balls inside the chassy
  private TalonSRX handle;
  private boolean handleS;

  // The extending solenoid 
  private Solenoid release;
  private boolean released = false;

  /**
   * @param intake Port for motor powering intake.
   * @param handle Port for internal handling system.
   * @param releasePort Port for release solenoid.
   * @param pulseDuration Duration of pulse to activate solenoid.
   */
  public BallSuck(int intake, int handle, int releasePort, double pulseDuration) {

    this.intake = new Talon(intake);
    intakeS = false;
    this.handle = new TalonSRX(handle);
    handleS = false;

    // release = new Solenoid(releasePort);
    // release.setPulseDuration(pulseDuration);

    setDefaultCommand(new BallIntake(this));
  }

  /**
   * System based on toggle for the intake and internal handling of the balls
   */
  public void activateRelease()
  {
    release.startPulse();
    released = true;
  }
  public boolean activatedRelease()
  {
    return released;
  }

  public void reverseIntake()
  {
    intake.set(-0.6);
    System.out.println("Spinning intake");
    intakeS = true;
  }

  // Initiate motor/systems
  public void turnOnIntake()
  {
    intake.set(0.6);
    System.out.println("Spinning intake");
    intakeS = true;
  }
  public void intakeBackwards()
  {
    //This should only be called to get us out of frame perimeter
    intake.set(-1.0);
    intakeS = true;
  }
  public void turnOffIntake()
  {
    intake.set(0);
    intakeS = false;
  }

  
  public boolean intakeStat()
  {
    return intakeS;
  }

  public void reverseHandle()
  {
    handle.set(ControlMode.PercentOutput, 0.5);
    handleS = true;
  }

  // Stops the motors
  public void turnOnHandle()
  {
    handle.set(ControlMode.PercentOutput, -0.5);
    handleS = true;
  }
  public void turnOffHandle()
  {
    handle.set(ControlMode.PercentOutput, 0);
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
