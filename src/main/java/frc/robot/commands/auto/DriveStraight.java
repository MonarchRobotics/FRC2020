/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.MotorControlPID;
import frc.robot.commands.BallIntake;
import frc.robot.subsystems.BallSuck;
import frc.robot.subsystems.Drivetrain;
//import edu.wpi.first.wpilibj.Timer;


/**
 * The auto command to drive forward
 */
public class DriveStraight extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drivetrain subsystem;
    private final BallSuck intake;
    double distanceToTravel;
    double travelSpeed;

    private MotorControlPID leftPid;
    private MotorControlPID rightPid;

    private double suckTime;
    private Timer timer;

    /**
     * @param subsystem The Drivetrain subsystem {@link Drivetrain} so that we can drive.
     * @param distance The distance we want to drive in inches.
     * @param speed The speed at which we want to travel
     * @param suckTime The number of seconds to suck for. If it is 0, it will not suck. If it is less than 0, it will suck the whole time
     */
    public DriveStraight(Drivetrain subsystem, BallSuck intake, double distance, double speed, double suckTime) {
        this.subsystem = subsystem;
        this.intake = intake;
        distanceToTravel = distance;
        travelSpeed = speed;
        this.suckTime = suckTime;
        if(travelSpeed<0){
            travelSpeed*=-1;
        }
        timer = new Timer();
        /**
         * Declare {@link subsystem} as a requirement of the command
         */
        addRequirements(subsystem);
        leftPid = new MotorControlPID(0.0,1.0,1.0,0.0015,0.00025);
        rightPid = new MotorControlPID(0.0,1.0,1.0,0.0015,0.00025);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        //reset the values of the encoders to zero.
        subsystem.getEncoderRight().reset();
        subsystem.getEncoderLeft().reset();
        leftPid = new MotorControlPID(0.0,1.0,1.0,0.0015,0.00025);
        rightPid = new MotorControlPID(0.0,1.0,1.0,0.0015,0.00025);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if(suckTime<0 || timer.get()<suckTime){
            intake.turnOnHandle();
            intake.turnOnIntake();
        }
        else{

            intake.turnOffHandle();
            intake.turnOffIntake();
        }
        //set the initial speed, before encoder adjustments

        //read the distance each encoder has traveled (in inches)
        double leftEnc = subsystem.getEncoderLeft().getDistance();
        double rightEnc = subsystem.getEncoderRight().getDistance();

        SmartDashboard.putNumber("leftEnc", leftEnc);
        SmartDashboard.putNumber("rightEnc", rightEnc);
        
        leftPid.setTarget(velocityCurve(travelSpeed,distanceToTravel - leftEnc));
        rightPid.setTarget(velocityCurve(travelSpeed,distanceToTravel - rightEnc));

        System.out.println("Target:"+leftPid.getTarget());
        
        double leftSpeed = leftPid.getSpeed(subsystem.getEncoderLeft().getRate());
        double rightSpeed = rightPid.getSpeed(subsystem.getEncoderRight().getRate());

        SmartDashboard.putNumber("leftSpeed", leftSpeed);
        SmartDashboard.putNumber("rightSpeed", rightSpeed);

        // System.out.println("S:"+leftSpeed);
        
        

        //        System.out.println("R:"+Math.round(rightEnc*100)/100.0+",L:"+Math.round(leftEnc*100)/100);
        //        //reverse our speed if we want to travel backwards
        //        if(distanceToTravel<0){
        //            rightSpeed*=-1;
        //            leftSpeed*=-1;
        //        }

        //adjust the right and left speed based on how far each side has traveled
        //changes the speed based on how far apart the two distances are
        //        if(rightEnc>leftEnc){
        //            rightSpeed = travelSpeed * (1-(rightEnc-leftEnc)/10.0);
        //        }
        //        else if(rightEnc<leftEnc){
        //            leftSpeed = travelSpeed * (1-(leftEnc-rightSpeed)/10.0);
        //        }
        subsystem.ldrive(leftSpeed);
        subsystem.rdrive(rightSpeed);
        // System.out.println(subsystem.getEncoderRight().getDistance()+"in");
    }

    // Called once the command ends or is interrupted, sets motors to stop moving
    @Override
    public void end(boolean interrupted) {
        subsystem.ldrive(0);
        subsystem.rdrive(0);
        intake.turnOffHandle();
        intake.turnOffIntake();
        // System.out.println("DONE");
    }

    double velocityCurve(double maxSpeed, double distanceFromTarget){
        double distanceToSlowDownAt100 = 60;//inches
        double velocityFromDistance = distanceFromTarget *distanceFromTarget/distanceToSlowDownAt100/distanceToSlowDownAt100+0.05;
        // System.out.println("vel:"+velocityFromDistance);
        // System.out.println("max:"+maxSpeed);
        // System.out.println("min:"+Math.min(maxSpeed, velocityFromDistance));
        return Math.min(maxSpeed, velocityFromDistance)*130* (distanceToTravel>0 ? 1 : -1);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        //returns false until we have traveled the correct distance on the encoders.
        return Math.abs(subsystem.getEncoderRight().getDistance())>Math.abs(distanceToTravel) && Math.abs(subsystem.getEncoderLeft().getDistance())>Math.abs(distanceToTravel);
        // return Math.abs(leftPid.getPreviousE()) < 0.25 && Math.abs(rightPid.getPreviousE()) < 0.25;
//        return subsystem.getEncoderRight().getDistance()>distanceToTravel-20*travelSpeed;
    }
}