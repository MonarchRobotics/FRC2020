package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.BallSuck;
import frc.robot.subsystems.WheelManipulator;
import frc.robot.commands.auto.GetOutOfFrame;

/**
* Collects commands used for autonomous
 *
 *
*/
public class AutoGroup extends SequentialCommandGroup {
    public AutoGroup(Turret turret, Drivetrain drivetrain, BallSuck ballsuck){
        //Sequentially runs SpinWheel, then DriveAuto
        addCommands(new DriveStraight(drivetrain,48,0.5));
        //        addCommands(new AutoShootBall(turret), new DriveAuto(drivetrain));

        // Moves the intake out of the frame perimiter
        addCommands(new GetOutOfFrame(ballsuck));
    }
}
