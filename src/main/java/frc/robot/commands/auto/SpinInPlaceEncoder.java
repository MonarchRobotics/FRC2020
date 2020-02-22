/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.MotorControlPID;
import frc.robot.subsystems.Drivetrain;
//import edu.wpi.first.wpilibj.Timer;


/**
 * The auto command to drive forward
 */
public class SpinInPlaceEncoder extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drivetrain drivetrain;
    private MotorControlPID leftPid;
    private MotorControlPID rightPid;

    /**
     * @param subsystem The subsystem used by this command.
     * @param degrees The amount we want to turn in degrees. Spinning clockwise is positive, counterclockwise is negative.
     * @param speed The speed we want to spin at.
     */
    public SpinInPlaceEncoder(Drivetrain subsystem, int degrees, double speed) {
        this.drivetrain = subsystem;

        double distanceToTravel = degrees/360.0 * Constants.getSpinCircumference();
        leftPid = new MotorControlPID(distanceToTravel,speed,1.0,0.1);
        rightPid = new MotorControlPID(-distanceToTravel,speed,1.0,0.1);

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        drivetrain.getEncoderLeft().reset();
        drivetrain.getEncoderRight().reset();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        double leftSpeed = leftPid.getSpeed(drivetrain.getEncoderLeft().getDistance());
        double rightSpeed = rightPid.getSpeed(drivetrain.getEncoderRight().getDistance());

        //travel at the speed
        drivetrain.ldrive(leftSpeed);
        drivetrain.rdrive(rightSpeed);
    }

    // Called once the command ends or is interrupted, sets motors to stop moving
    @Override
    public void end(boolean interrupted) {
        drivetrain.ldrive(0);
        drivetrain.rdrive(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        //returns false until we have spun the correct amount (our error is essentially 0)
        return Math.abs(leftPid.getPreviousE())<0.25 && Math.abs(rightPid.getPreviousE()) < 0.25;
    }
}