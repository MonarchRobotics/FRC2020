/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Arrays;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.subsystems.WheelManipulator;
import org.opencv.core.Core;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;


  static NetworkTableInstance inst = NetworkTableInstance.getDefault();
  static NetworkTable table = inst.getTable("GRIP/findLoadingStation");
  static NetworkTable linesTable = inst.getTable("GRIP/linesReport");

  private WheelManipulator wheelManipulator;
  private OI oi;


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    table.delete("height");
    oi = new OI();

    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
    camera.setResolution(320, 240);
    // camera.setBrightness(4);
    camera.setExposureManual(3);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.

    

    // System.out.println("W:"+Arrays.toString(widthEntry.getDoubleArray(new double[0])));
    // System.out.println("H:"+Arrays.toString(heightEntry.getDoubleArray(new double[0])));

    CommandScheduler.getInstance().run();

  }

  public static double[] getCoordinates(){
    //Get the widths and heights from GRIP
    double[] widths;
    widths = table.getEntry("width").getDoubleArray(new double[0]);
    double[] heights;
    heights = table.getEntry("height").getDoubleArray(new double[0]);

    //make sure we can actually see something on the camera
    if(widths.length>0 && heights.length>0){
      //get the width and height of the target in pixels, since there should only be one object
      double stationWidth = widths[0];
      double stationHeight = heights[0];
      //get a list of the lines the camera sees
      double[] x1s = linesTable.getEntry("x1").getDoubleArray(new double[0]);
      double[] x2s = linesTable.getEntry("x2").getDoubleArray(new double[0]);
      double[] y1s = linesTable.getEntry("y1").getDoubleArray(new double[0]);
      double[] y2s = linesTable.getEntry("y2").getDoubleArray(new double[0]);

      //get the center of the target in the camera viewport
      double centerX = table.getEntry("centerX").getDoubleArray(new double[0])[0];
      double centerY = table.getEntry("centerY").getDoubleArray(new double[0])[0];

      //sanity check
      //this should always return true. If it doesn't we have BIG problems
      if(y2s.length > 0 && x1s.length == x2s.length && x1s.length == y1s.length && x1s.length == y2s.length){
        //create a list of coordinates from the lines
        double[][] cords = new double[x1s.length*2][2];//0 is x, 1 is y

        //add all of the coordinates to the new array
        for(int i=0; i<x1s.length; i++){
          double[] set = new double[2];
          set[0] = x1s[i];
          set[1] = y1s[i];
          cords[i*2] = set;
          double[] set2 = new double[2];
          set2[0] = x2s[i];
          set2[1] = y2s[i];
          cords[i*2+1] = set2;
        }

        //set up variables to calculate the corners of the box
        double distanceTopRight =0;
        double[] topRight = new double[2];
        double distanceTopLeft = 0;
        double[] topLeft = new double[2];
        double distanceBottomRight = 0;
        double[] bottomRight = new double[2];
        double distanceBottomLeft = 0;
        double[] bottomLeft = new double[2];

        //find the coordinates farthest from the center location of the target.
        for(int i=0; i<cords.length; i++){
          double[] cord = cords[i];
          double distance = Math.pow(cord[0]-centerX,2)+Math.pow(cord[1]-centerY,2);
          if(cord[0]>centerX && cord[1]>centerY){//topRight
            if(distance>distanceTopRight){
              distanceTopRight = distance;
              topRight = cord;
            }
          }
          else if(cord[0]<centerX && cord[1]>centerY){//topLeft
            if(distance>distanceTopLeft){
              distanceTopLeft = distance;
              topLeft = cord;
            }
          }
          else if(cord[0]>centerX && cord[1]<centerY){//bottomRight
            if(distance>distanceBottomRight){
              distanceBottomRight = distance;
              bottomRight = cord;
            }
          }
          else if(cord[0]<centerX && cord[1]<centerY){//bottomLeft
            if(distance>distanceBottomLeft){
              distanceBottomLeft = distance;
              bottomLeft = cord;
            }
          }
        }

        //calculate the straight distance using a calibration curve
        double distanceFromHeight = 4298.880337*Math.pow(stationHeight,-1.020576785);

        //get the ratio of the adjacent side to the hypotenuse of the triangle to the port
        double cosRatio = stationWidth / (stationHeight*2.0/3.0);

        // Cos doesnt work on values greater than 1
          // Assumes if it is very close to 1 it should be 1
        if(cosRatio>1){//this happens if we are close to straight on
          cosRatio = 1;
        }

        //calculate the angle we are from the target in radians.
        double angle = Math.acos(cosRatio);

        /* Finds the distance from the center of the port, and takes into 
        account he fact that we might not be centered on the target */
        // Finds the distance from the camera to the center of the retro-reflective tape
        double tempAdjustedDistance = Math.sqrt(12.25 + Math.pow(distanceFromHeight, 2) - 
          2*3.5*distanceFromHeight*Math.cos(angle + Math.PI/2));
        double a = stationWidth * 7.0 /  Math.abs(160-centerX);
        double adjustedDistance = Math.sqrt(Math.pow(a,2) + Math.pow(tempAdjustedDistance, 2) - 
          2*a*tempAdjustedDistance*Math.cos(angle + Math.PI/2));

        //convert the final distance and radian angle to coordinates.
        int xCords, yCords;

        xCords = Math.round((float) (adjustedDistance*Math.cos(Math.PI/2 - angle)));
        yCords = Math.round((float) (adjustedDistance*Math.sin(Math.PI/2 - angle)));

        //check to see which side of the target we are on, and convert the x coordinates accordingly.
        if(topRight[1]<topLeft[1] && bottomLeft[1]<bottomRight[1]){
          xCords*=-1;
        }
        // Stores the cordinates and the angle in a format to be returned
        double[] finalCoordinates = new double[3];
        finalCoordinates[0] = xCords; //pos. is right of target, neg. is left of target
        finalCoordinates[1] = yCords; //distance from target
        finalCoordinates[2] = angle; //angle in radians
        return finalCoordinates;
      }
    }
    return new double[0];
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
