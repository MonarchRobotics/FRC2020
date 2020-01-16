/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.OI;
import frc.robot.subsystems.PullUp;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;


import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj2.command.CommandBase;


/**
 * The Pnumatic climbing system
 */
public class Climb extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final PullUp pullUp;
  
  


  /**
   * @param subsystem The subsystem used by this command.
   */
  public Climb(PullUp subsystem) {
    pullUp = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);



    // camera = new VideoCapture(0);
    // if(camera.isOpened()){
    //   System.out.println("Camera is ready");
    // }

  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("myContoursReport");
    System.out.print(table.getKeys());

    // When A on the xbox controller is pressed toggle climbing
    if (OI.xboxController.getAButtonPressed()) {
      if (pullUp.getValue() != DoubleSolenoid.Value.kForward) {
        pullUp.extendClimb();
      }
      else {
        pullUp.retractClimb();
      }
    }
    
    // Mat frame = new Mat();
    // camera.read(frame);

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