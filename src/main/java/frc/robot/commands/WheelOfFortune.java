/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.DriverStation;


/**
 * An example command that uses an example subsystem.
 */
public class WheelOfFortune extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  // VideoCapture camera;

  private boolean doingRotation;
  private boolean doingPosition;

  private int[] countedColors;
  private int lastDetectedColor;
  private int colorForPosition;


  public WheelOfFortune() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.wheelManipulator);
    doingRotation = false;
    doingPosition = false;
    countedColors = new int[4];//0=R,1=Y,2=B,3=G
    for(int i=0; i<countedColors.length; i++){
      countedColors[i]=0;
    }
    lastDetectedColor = 0;//0=none,1=R,2=Y,3=B,4=G
    colorForPosition = -1;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
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
        switch (gameData.charAt(0))
        {
          case 'B' :
            colorForPosition = 0;//detect red
            break;
          case 'G' :
            colorForPosition = 1;//detect yellow
            break;
          case 'R' :
            colorForPosition = 2;//detect blue
            break;
          case 'Y' :
            colorForPosition = 3;//detect green
            break;
          default :
            colorForPosition = -1;//data is corrupt
            break;
        }
      }
    }

    //button checking to activate the two modes
    if(1==2 && !doingPosition && !doingRotation){//some button on the joystick/controller for Rotation control
      doingRotation = true;
      lastDetectedColor = 0;
      for(int i=0; i<countedColors.length; i++){
        countedColors[i]=0;
      }
    }
    else if (1==3 && !doingPosition && !doingRotation){//some button to activate for Position control
      if(colorForPosition!=-1){
        doingPosition = true;
      }
      else{
        System.out.println("No color received from driver station yet");
      }
    }

    int detectedColor = Robot.wheelManipulator.detectColor();
    //the actual logic for the wheels to work
    if(doingRotation && doingPosition){
      //This should NEVER get triggered, if it does I'm probably an idiot but I'm putting it here just in case I screw something up.
      doingPosition = false;
      doingRotation = false;
    }
    else if(doingRotation){
      Robot.wheelManipulator.getSpinnerMotor().set(ControlMode.Current,1.0);
      if(detectedColor!=lastDetectedColor){
        lastDetectedColor = detectedColor;
        if(detectedColor!=0){
          countedColors[detectedColor-1]++;
          if(countedColors[0]>=7 && countedColors[1]>=7 && countedColors[2]>=7 && countedColors[3]>=7){
            System.out.println("STOP. ROTATION IS DONE");
            Robot.wheelManipulator.getSpinnerMotor().set(ControlMode.Current,0.0);
            doingRotation = false;
          }
        }
      }


    }
    else if(doingPosition){
      Robot.wheelManipulator.getSpinnerMotor().set(ControlMode.Current,1.0);
      if(detectedColor!=lastDetectedColor){
        lastDetectedColor = detectedColor;
        if(detectedColor==colorForPosition){
          System.out.println("STOP. POSITION IS DONE");
          Robot.wheelManipulator.getSpinnerMotor().set(ControlMode.Current,0.0);
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