/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
//import edu.wpi.first.wpilibj.Timer;


/**
 * The auto command to drive forward
 */
public class DriveStraight extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drivetrain subsystem;
    double distanceToTravel;
    double travelSpeed;

    /**
     * @param subsystem The subsystem used by this command.
     * @param distance The distance we want to drive in inches.
     * @param speed The speed at which we want to travel
     */
    public DriveStraight(Drivetrain subsystem, double distance, double speed) {
        this.subsystem = subsystem;

        distanceToTravel = distance;
        travelSpeed = speed;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

        //reset the values of the encoders to zero.
        subsystem.getEncoderRight().reset();
        subsystem.getEncoderLeft().reset();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        //set the initial speed, before encoder adjustments
        double rightSpeed = travelSpeed;
        double leftSpeed = travelSpeed;

        //read the distance each encoder has traveled (in inches)
        double leftEnc = subsystem.getEncoderLeft().getDistance();
        double rightEnc = subsystem.getEncoderRight().getDistance();

        System.out.println("R:"+Math.round(rightEnc*100)/100.0+",L:"+Math.round(leftEnc*100)/100);
        //reverse our speed if we want to travel backwards
        if(distanceToTravel<0){
            rightSpeed*=-1;
            leftSpeed*=-1;
        }

        //adjust the right and left speed based on how far each side has traveled
        //changes the speed based on how far apart the two distances are
        if(rightEnc>leftEnc){
            rightSpeed = travelSpeed * (1-(rightEnc-leftEnc)/10.0);
        }
        else if(rightEnc<leftEnc){
            leftSpeed = travelSpeed * (1-(leftEnc-rightSpeed)/10.0);
        }
        subsystem.ldrive(leftSpeed);
        subsystem.rdrive(rightSpeed);
        System.out.println(subsystem.getEncoderRight().getDistance()+"in");
    }

    // Called once the command ends or is interrupted, sets motors to stop moving
    @Override
    public void end(boolean interrupted) {
        subsystem.ldrive(0);
        subsystem.rdrive(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        //returns false until we have traveled the correct distance on the encoders.
        return subsystem.getEncoderRight().getDistance()>distanceToTravel-20*travelSpeed;
    }
}