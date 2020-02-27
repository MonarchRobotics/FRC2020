package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.BallSuck;

/**
* Collects commands used for autonomous
 *
 *
*/
public class AutoGroup extends SequentialCommandGroup {
    /**
     * @param turret The Turret Subsystem {@link Turret} so that we can shoot balls
     * @param ballsuck The BallSuck Subsystem {@link BallSuck} so that we can SUCC balls
     * @param drivetrain The Drivetrain Subsystem {@link Drivetrain} so that we can drive!
     * */
    public AutoGroup(Turret turret, Drivetrain drivetrain, BallSuck ballsuck){
        //get the ballsuck system out of frame perimeter at the start of the match
        addCommands(new AutoInit(ballsuck,drivetrain));
        addCommands(new DriveStraight(drivetrain,-27,0.35));
        addCommands(new AutoShootBall(turret,28));
    }
}
