/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.auto;


import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.Constants;
import frc.robot.subsystems.WheelManipulator;


import frc.robot.OI;


import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;


/**
 * The Pnumatic climbing system
 */
public class SpinWheel extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final WheelManipulator subsystem;




    /**
     * @param subsystem The subsystem used by this command.
     */
    public SpinWheel(WheelManipulator subsystem) {
        this.subsystem = subsystem;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);



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
        subsystem.getSpinnerMotor().set(ControlMode.PercentOutput, 0.5);
    }



    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        subsystem.getSpinnerMotor().set(ControlMode.PercentOutput, 0.0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}