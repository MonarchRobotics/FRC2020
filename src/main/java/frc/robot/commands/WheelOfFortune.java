/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.WheelManipulator;
import edu.wpi.first.wpilibj.DriverStation;


/**
 * Rotation and position control using a colour sensor for the control panel.
 */
public class WheelOfFortune extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  // VideoCapture camera;

  private boolean doingRotation;
  private boolean doingPosition;

  private int[] countedColors;
  private int lastDetectedColor;
  private int colorForPosition;
  private final WheelManipulator subsystem;


  public WheelOfFortune(WheelManipulator subsystem) {
    this.subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    doingRotation = false;
    doingPosition = false;
    countedColors = new int[4];//0=R,1=Y,2=B,3=G
    for(int i=0; i<countedColors.length; i++){
      countedColors[i]=0;
    }
    lastDetectedColor = 0;//0=none,1=R,2=Y,3=B,4=G
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    colorForPosition = -1;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //check if we've received anything from the Driver Station
    if(colorForPosition==-1){
      String gameData;
      gameData = DriverStation.getInstance().getGameSpecificMessage();
      if(gameData.length() > 0)
      {
        System.out.println("We received data: "+gameData);
        switch (gameData.charAt(0)){//colors are offset because we detect from the middle of the wheel, but the field sensor is on the side. So in theory, if we see blue, the field senses red, etc.
          case 'B' :
            colorForPosition = 1;//detect red
            break;
          case 'G' :
            colorForPosition = 2;//detect yellow
            break;
          case 'R' :
            colorForPosition = 3;//detect blue
            break;
          case 'Y' :
            colorForPosition = 4;//detect green
            break;
          default :
            colorForPosition = -1;//data is corrupt
            break;
        }
      }
    }
    boolean bButton = OI.xboxController.getBButtonPressed();
    boolean xButton = OI.xboxController.getXButtonPressed();
    //button checking to activate the two modes
    if(bButton && !doingPosition && !doingRotation){//some button on the joystick/controller for Rotation control
      System.out.println("Starting rotational control...");
      doingRotation = true;
      lastDetectedColor = 0;
      for(int i=0; i<countedColors.length; i++){
        countedColors[i]=0;
      }
    }
    else if (xButton && !doingPosition && !doingRotation){//some button to activate for Position control
      if(colorForPosition!=-1){
        System.out.println("Starting position control...");
        doingPosition = true;
      }
      else{
        System.out.println("No color received from driver station yet");
      }
    }
    else if(bButton && doingRotation && !doingPosition){
      doingRotation = false;
      subsystem.getSpinnerMotor().set(ControlMode.PercentOutput,0.0);
      System.out.println("Stopped rotation control");
    }
    else if(xButton && !doingRotation && doingPosition){
      doingPosition = false;
      subsystem.getSpinnerMotor().set(ControlMode.PercentOutput,0.0);
      System.out.println("Stopped position control");
    }

    int detectedColor = subsystem.detectColor();
    // System.out.println(detectedColor);
    //the actual logic for the wheels to work
    if(doingRotation && doingPosition){
      //This should NEVER get triggered, if it does I'm probably an idiot but I'm putting it here just in case I screw something up.
      doingPosition = false;
      doingRotation = false;
    }
    else if(doingRotation){
      subsystem.getSpinnerMotor().set(ControlMode.PercentOutput,1.0);
      if(detectedColor!=lastDetectedColor){//The color has changed
        lastDetectedColor = detectedColor;
        System.out.println(detectedColor);
        if(detectedColor!=0){
          countedColors[detectedColor-1]++;//add to the count of the number of times the sensor has seen each color
          if(countedColors[0]>=7 && countedColors[1]>=7 && countedColors[2]>=7 && countedColors[3]>=7){//once we've seen each color 7 times (so 3.5 rotations), stop it from rotating.
            System.out.println("STOP. ROTATION IS DONE");
            System.out.println("STOP. ROTATION IS DONE");
            System.out.println("STOP. ROTATION IS DONE");
            System.out.println("STOP. ROTATION IS DONE");
            System.out.println("STOP. ROTATION IS DONE");
            subsystem.getSpinnerMotor().set(ControlMode.PercentOutput,0.0);
            doingRotation = false;
          }
        }
      }


    }
    else if(doingPosition){
      subsystem.getSpinnerMotor().set(ControlMode.PercentOutput,1.0);
      if(detectedColor!=lastDetectedColor){//the color has changed
        lastDetectedColor = detectedColor;
        System.out.println(lastDetectedColor);
        if(detectedColor==colorForPosition){//we have arrived at the correct color, so stop rotating
          System.out.println("STOP. POSITION IS DONE");
          System.out.println("STOP. POSITION IS DONE");
          System.out.println("STOP. POSITION IS DONE");
          System.out.println("STOP. POSITION IS DONE");
          System.out.println("STOP. POSITION IS DONE");
          colorForPosition = -1;
          subsystem.getSpinnerMotor().set(ControlMode.PercentOutput,0.0);
          doingPosition = false;
        }
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