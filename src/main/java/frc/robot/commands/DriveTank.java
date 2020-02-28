/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.MotorControlPID;
import frc.robot.OI;
import frc.robot.enums.WheelManipulatorState;
import frc.robot.subsystems.Drivetrain;
import frc.robot.Robot;



import edu.wpi.first.wpilibj2.command.CommandBase;



/**
 * Shockingly, it's how we drive.
 */
public class DriveTank extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drivetrain drivetrain;
    private final Timer timer;
    private double endTurn;
    private double spinSpeed;

    private int turnEndCheck;

    private MotorControlPID spinControl;

    private MotorControlPID leftSide, rightSide;


    private double leftSpeed, rightSpeed;

    /**
     * Creates a new ExampleCommand.
     *
     * @param drive The subsystem used by this command.
     */
    public DriveTank(Drivetrain drive) {
        drivetrain = drive;
        timer = new Timer();
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drive);

    }

    /** 
     * Initialises the encoders and the gyro
     * Also resets the timer
     * Adds the next "end turn" value for the rest of the class to execute a turn to that degree
     */
    @Override
    public void initialize() {
        timer.reset();
        drivetrain.getGyro().reset();
        drivetrain.initializeLidar();
        // endTurn = 0;
        endTurn = drivetrain.getGyro().getAngle() + 45;

        // Initialize turning a certain degree
        turnEndCheck = 0;
        spinSpeed = 0;
        leftSide = new MotorControlPID(0.0,1.0,1.0,0.25);
        rightSide = new MotorControlPID(0.0,1.0,1.0,0.25);
        
        leftSpeed = 0;
        rightSpeed = 0;

    }

    
    /**
     * When the left button 7 is pressed turnes to the predefined angle relative to the initialised value
     * Drives with speeds based off the 2 joysticks
     */
    @Override
    public void execute() {
        // System.out.println(drivetrain.getEncoderRight().getDistance());
        // System.out.println("D"+drivetrain.getEncoderLeft().getDistance());
        // System.out.println("R"+drivetrain.getEncoderLeft().getRate());

        
        // double rotateTime;

        System.out.println("Gyro:"+drivetrain.getGyro().getAngle());
        // encoder.reset();

        // System.out.println("D: "+encoder.getDistance());
        
        //If this breaks change to x
//            drivetrain.ldrive(OI.rightJoystick.getY());
//            drivetrain.rdrive(OI.leftJoystick.getY());
//
        // if(OI.leftButton7.get()){
            
        // }

        // System.out.println("Switch: "+drivetrain.getAutoSwitch().get());

        if(OI.leftButton7.get()){
            // TEST THING for turning 90 degrees
            if (drivetrain.getGyro().getAngle() % 360 < endTurn && turnEndCheck < 4)
            {
                turnEndCheck = 0;
                drivetrain.rdrive(-0.25);
                drivetrain.ldrive(0.25);
            }
            else if (drivetrain.getGyro().getAngle() % 360 > endTurn)
            {
                turnEndCheck++;
            }
            else
            {
                turnEndCheck = 0;
                drivetrain.rdrive(-OI.deadZone(OI.rightJoystick.getY(), Constants.getDeadZone()));
                drivetrain.ldrive(-OI.deadZone(OI.leftJoystick.getY(), Constants.getDeadZone()));
            }
        }
        else{
            // System.out.println("JSR:"+OI.leftJoystick.getY()*-1);
            // System.out.println("RPS:"+drivetrain.getEncoderLeft().getRate());
            // System.out.println("L:"+drivetrain.getEncoderLeft().getRate());
            // System.out.println("R:"+drivetrain.getEncoderRight().getRate());
            // System.out.println("Lidar Reading:"+drivetrain.getLidarMeasurement());
            System.out.println("Coords:"+Robot.getTargetCenterCoordinates()[0]);
            turnEndCheck = 0;
            if(!OI.rightJoystick.getTrigger() && !OI.leftJoystick.getTrigger()){
                leftSide.setTarget(-OI.deadZone(OI.rightJoystick.getY(), Constants.getDeadZone()));
                rightSide.setTarget(-OI.deadZone(OI.leftJoystick.getY(), Constants.getDeadZone()));

                leftSpeed = leftSide.getSpeed(leftSpeed);
                rightSpeed = rightSide.getSpeed(rightSpeed);

                // drivetrain.rdrive(rightSpeed);
                // drivetrain.ldrive(leftSpeed);
                drivetrain.rdrive(-OI.deadZone(OI.rightJoystick.getY(), Constants.getDeadZone()));
                drivetrain.ldrive(-OI.deadZone(OI.leftJoystick.getY(), Constants.getDeadZone()));
            }
        }

//        if (OI.rightJoystick.getTriggerPressed()) {
//            if (Robot.getCoordinates().length > 0) {
//                double startAngle = Robot.getCoordinates()[2];
//                if (Robot.getCoordinates()[2] >= 0) {
//                    rotateTime = Constants.getTimeToRotate(Math.PI/2, .25);
//                }
//                else if (Robot.getCoordinates()[2] < 0) {
//                }
//                timer.start();
//                drivetrain.ldrive(.25);
//                drivetrain.rdrive(-.25);
//            }
//        }
//        else { rotateTime = 0; }
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