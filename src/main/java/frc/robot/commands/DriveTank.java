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
        // endTurn = 0;
        endTurn = drivetrain.getGyro().getAngle() + 45;

        // Initialize turning a certain degree
        turnEndCheck = 0;
        spinSpeed = 0;
        spinControl = new MotorControlPID(160,0.5,0.5,0.001,0.001);

    }

    
    /**
     * When the left button 7 is pressed turnes to the predefined angle relative to the initialised value
     * Drives with speeds based off the 2 joysticks
     */
    @Override
    public void execute() {
        // System.out.println(drivetrain.getEncoderRight().getDistance());
        System.out.println("D"+drivetrain.getEncoderLeft().getDistance());
        System.out.println("R"+drivetrain.getEncoderLeft().getRate());

        
        // double rotateTime;

        // System.out.println(drivetrain.getGyro().getAngle());
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
            if(OI.leftJoystick.getRawButton(11)){
                //TESTING system: find the target and rotate so that we are facing it
                double[] coords = Robot.getTargetCenterCoordinates();
                if(coords[0]!=-1){
                    spinSpeed = spinControl.getSpeed(coords[0]);
//                    if(coords[0]<160){
//                        double speed = (160-coords[0])/640;
//                        if(speed>0){speed=1;}
//                        spinSpeed = speed;
//                    }
//                    else if(coords[0]>160){
//                        double speed = (coords[0]-160)/640;
//                        if(speed>0){speed=1;}
//                        spinSpeed = -speed;
//                    }
//                    else{
//                        spinSpeed = 0;
//                    }
                }
                // System.out.println("spinSpeed: "+spinSpeed);
                drivetrain.ldrive(-spinSpeed);
                drivetrain.rdrive(spinSpeed);
            }
            else{
                
                // System.out.println("JSR:"+OI.leftJoystick.getY()*-1);
                // System.out.println("RPS:"+drivetrain.getEncoderLeft().getRate());
                // System.out.println("L:"+drivetrain.getEncoderLeft().getRate());
                // System.out.println("R:"+drivetrain.getEncoderRight().getRate());
                // System.out.println("Lidar Reading:"+drivetrain.getLidarMeasurement());
                turnEndCheck = 0;
                if(!OI.rightJoystick.getTrigger() && !OI.leftJoystick.getTrigger() && Robot.wheelManipulatorState == WheelManipulatorState.none){
                    drivetrain.rdrive(-OI.deadZone(OI.rightJoystick.getY(), Constants.getDeadZone()));
                    drivetrain.ldrive(-OI.deadZone(OI.leftJoystick.getY(), Constants.getDeadZone()));
                }
                
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