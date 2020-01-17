package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.WheelManipulator;

public class AutoGroup extends SequentialCommandGroup {
    AutoGroup(WheelManipulator wheelManipulator, Drivetrain drivetrain){
        addCommands(new SpinWheel(wheelManipulator));
    }
}
