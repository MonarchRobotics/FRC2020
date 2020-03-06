/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.MotorControlPID;
import frc.robot.subsystems.Drivetrain;
//import edu.wpi.first.wpilibj.Timer;


/**
 * The auto command to drive forward
 */
public class SpinInPlaceGyro extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drivetrain subsystem;
    private final MotorControlPID gyroPid;
    double degrees;
    double speed;
    double initialGyro;

    /**
     * @param subsystem The subsystem used by this command.
     * @param degrees The amount we want to turn in degrees. Spinning right is positive, left is negative.
     * @param speed The speed we want to spin at.
     */
    public SpinInPlaceGyro(Drivetrain subsystem, int degrees, double speed) {
        this.subsystem = subsystem;

        this.degrees = degrees;//positive means to turn right, negative is turning left
        this.speed = speed;
        gyroPid = new MotorControlPID(degrees,1.0,0.3,0.0003,0.00001);
        if(degrees<0){
            this.speed = -1;
        }
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        subsystem.getGyro().reset();
        //for now we're not resetting the gyroscope, but we may eventually
        initialGyro = subsystem.getGyro().getAngle();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println("Gyro:"+subsystem.getGyro().getAngle());
        System.out.println("Targ:"+gyroPid.getTarget());
        // double spinSpeed = 0.25;
        double spinSpeed = gyroPid.getSpeed(subsystem.getGyro().getAngle());
        SmartDashboard.putNumber("coordX", subsystem.getGyro().getAngle());
        SmartDashboard.putNumber("speed", spinSpeed);
        subsystem.ldrive(spinSpeed);
        subsystem.rdrive(-spinSpeed);

        //travel at the speed
//        subsystem.ldrive(speed);
//        subsystem.rdrive(-speed);
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
        //returns false until we have spun the correct amount
        return false;
    }
}