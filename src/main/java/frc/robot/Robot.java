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
//import frc.robot.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  public static WheelManipulator wheelManipulator = new WheelManipulator(0);//TODO: replace with the correct port (and it should probably be in RobotMap)


//  public static ExampleSubsystem m_subsystem = new ExampleSubsystem();

  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  NetworkTable table = inst.getTable("GRIP/myContoursReport");
  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
  int lastDetectedColor = 0; //0=none,1=R,2=Y,3=G,4=B

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

    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
    camera.setResolution(320, 240);
    camera.setBrightness(4);
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
    NetworkTableEntry areaEntry = table.getEntry("area");
    // System.out.println(Arrays.toString(areaEntry.getDoubleArray(new double[0])));

//    Color color = colorSensor.getColor();
//    int thisColor = 0;
//    if(colorSensorMargin(color.red,0.54) && colorSensorMargin(color.green,0.35) && colorSensorMargin(color.blue,0.11)){//red
//      thisColor = 1;
//    }
//    else if(colorSensorMargin(color.red,0.37) && colorSensorMargin(color.green,0.54) && colorSensorMargin(color.blue,0.10)){//yellow
//      thisColor = 2;
//    }
//    else if(colorSensorMargin(color.red,0.18) && colorSensorMargin(color.green,0.53) && colorSensorMargin(color.blue,0.28)){//green
//      thisColor = 3;
//    }
//    else if(colorSensorMargin(color.red,0.15) && colorSensorMargin(color.green,0.44) && colorSensorMargin(color.blue,0.41)){//green
//      thisColor = 4;
//    }
//    if(thisColor!=lastDetectedColor){
//      lastDetectedColor = thisColor;
//      switch (thisColor){
//        case 1:
//          System.out.println("Color changed: red");
//          break;
//        case 2:
//          System.out.println("Color changed: yellow");
//          break;
//        case 3:
//          System.out.println("Color changed: green");
//          break;
//        case 4:
//          System.out.println("Color changed: blue");
//          break;
//        default:
//          System.out.println("Color changed: none");
//          break;
//      }
//    }

    int proximity = colorSensor.getProximity();
    // System.out.println("Proximity:"+proximity);

    CommandScheduler.getInstance().run();

    
    // System.out.print(table.getKeys());
  }

  boolean colorSensorMargin(double detected, double test){
    double error = 0.03;
    return detected<=test+error && detected>=test-error;
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
