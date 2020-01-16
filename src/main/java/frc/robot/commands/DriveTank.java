/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.OI;
import frc.robot.subsystems.Drivetrain;



import edu.wpi.first.wpilibj2.command.CommandBase;



/**
 * An example command that uses an example subsystem.
 */
public class DriveTank extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Drivetrain drivetrain;
    /**
     * Creates a new ExampleCommand.
     *
     * @param drive The subsystem used by this command.
     */
    public DriveTank(Drivetrain drive) {
        drivetrain = drive;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drive);
        // camera = new VideoCapture(0);
        // if(camera.isOpened()){
        //   System.out.println("Camera is ready");
        // }
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        //If this breaks change to x
//            drivetrain.ldrive(OI.joystick1.getY());
//            drivetrain.rdrive(OI.joystick2.getY());
            drivetrain.ldrive(OI.xboxController.getY(GenericHID.Hand.kLeft));
            drivetrain.rdrive(OI.xboxController.getY(GenericHID.Hand.kRight));


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