package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.WheelManipulator;

/**
* Collects commands used for autonomous
*/
public class AutoGroup extends SequentialCommandGroup {
    public AutoGroup(WheelManipulator wheelManipulator, Drivetrain drivetrain){
        //Sequentially runs SpinWheel, then DriveAuto
        addCommands(new SpinWheel(wheelManipulator), new DriveAuto(drivetrain));
    }
}
