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
import frc.robot.OI;
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
    Encoder encoder = new Encoder(0, 1);

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
        encoder.setDistancePerPulse(1.0/2048.0);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        encoder.reset();
        timer.reset();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        
        double rotateTime;
        // encoder.reset();

        // System.out.println("D: "+encoder.getDistance());
        
        //If this breaks change to x
//            drivetrain.ldrive(OI.joystick1.getY());
//            drivetrain.rdrive(OI.joystick2.getY());
//
       drivetrain.rdrive(-OI.deadZone(OI.joystick1.getY(), Constants.getDeadZone()));
       drivetrain.ldrive(-OI.deadZone(OI.joystick2.getY(), Constants.getDeadZone()));

//        if (OI.joystick1.getTriggerPressed()) {
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

    // Driving test
    public void turnFullSP()
    {
        drivetrain.rdrive(-1);
        drivetrain.ldrive(1);
    }

    //Good stuff but not necessary (just for testing)
    public void FullStop()
    {
        drivetrain.rdrive(0);
        drivetrain.ldrive(0);
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