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

//  public static ExampleSubsystem m_subsystem = new ExampleSubsystem();

  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable table = inst.getTable("GRIP/findLoadingStation");
  NetworkTable linesTable = inst.getTable("GRIP/linesReport");

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
    NetworkTableEntry xEntry = table.getEntry("x");
    xEntry.setDouble(5.0);
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
    double[] widths = table.getEntry("width").getDoubleArray(new double[0]);
    double[] heights = table.getEntry("height").getDoubleArray(new double[0]);

    if(widths.length>0 && heights.length>0){
      double stationWidth = widths[0];
      double stationHeight = heights[0];
      double[] x1s = linesTable.getEntry("x1").getDoubleArray(new double[0]);
      double[] x2s = linesTable.getEntry("x2").getDoubleArray(new double[0]);
      double[] y1s = linesTable.getEntry("y1").getDoubleArray(new double[0]);
      double[] y2s = linesTable.getEntry("y2").getDoubleArray(new double[0]);
      double centerX = table.getEntry("centerX").getDoubleArray(new double[0])[0];
      double centerY = table.getEntry("centerY").getDoubleArray(new double[0])[0];

      if(y2s.length > 0 && x1s.length == x2s.length && x1s.length == y1s.length && x1s.length == y2s.length){//this should always return true. If it doesn't we have BIG problems
        double[][] cords = new double[x1s.length*2][2];//0 is x, 1 is y

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
        
        double distanceTopRight =0;
        double[] topRight = new double[2];
        double distanceTopLeft = 0;
        double[] topLeft = new double[2];
        double distanceBottomRight = 0;
        double[] bottomRight = new double[2];
        double distanceBottomLeft = 0;
        double[] bottomLeft = new double[2];
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
        // double distanceFromWidth = 2037.642978* Math.pow(stationWidth, -0.930927117);
        double distanceFromHeight = 4298.880337*Math.pow(stationHeight,-1.020576785);
        // System.out.println(stationWidth/stationHeight);
        double angle = Math.acos(stationWidth / (stationHeight*2.0/3.0));
        // System.out.println("Distance: "+distanceFromHeight+"in");
        // Adjusted distance
        double adjustedDistance = Math.sqrt(12.25 + Math.pow(distanceFromHeight, 2) - 2*3.5*distanceFromHeight*Math.cos(angle + Math.PI/2));
        
        System.out.println("Distance: "+adjustedDistance+"in");

        if(topRight[1]>topLeft[1] && bottomLeft[1]>bottomRight[1]){
          System.out.println("Angle:-"+ angle+"rad");
        }
        else if(topRight[1]<topLeft[1] && bottomLeft[1]<bottomRight[1]){
          System.out.println("Angle: "+ angle+"rad");
        }
        else{
          System.out.println("PRBLM: "+angle+"rad");
        }

        // System.out.println("Ratio: "+stationHeight/stationWidth);
      }
    }
    

    // System.out.println("W:"+Arrays.toString(widthEntry.getDoubleArray(new double[0])));
    // System.out.println("H:"+Arrays.toString(heightEntry.getDoubleArray(new double[0])));

    CommandScheduler.getInstance().run();

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
