package frc.robot.commands.auto;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BallSuck;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Turret;

public class AutoGroupFinal extends SequentialCommandGroup {
    /**
     * @param turret The Turret Subsystem {@link Turret} so that we can shoot balls
     * @param ballsuck The BallSuck Subsystem {@link BallSuck} so that we can SUCC balls
     * @param drivetrain The Drivetrain Subsystem {@link Drivetrain} so that we can drive!
     * */
    public AutoGroupFinal(Turret turret, Drivetrain drivetrain, BallSuck ballsuck){
        //get the ballsuck system out of frame perimeter at the start of the match
        addCommands(new ParallelCommandGroup(
                new AutoInit(ballsuck,drivetrain),
                new SpinToPort(drivetrain),
                new AutoShootBall(turret,28),
                new SpinInPlaceGyro(drivetrain,0,0.0)
        ));
        if(drivetrain.getAutoSwitch().get()) {
            //we are in front of the trench run, so go pick up the balls.
            addCommands(
                    new DriveStraight(drivetrain, ballsuck, -193, 0.20, -1),
                    new DriveStraight(drivetrain, ballsuck, 100, 0.20, 5)
            );
            addCommands(new ParallelCommandGroup(
                    new SpinToPort(drivetrain),
                    new AutoShootBall(turret, 28)
            ));
        }
        else{
            //back up off the initiation line for 5 points.
            addCommands(
                    new DriveStraight(drivetrain, ballsuck, -50, 0.20, 0)
            );
        }
        // addCommands(new DriveStraight(drivetrain,-27,0.35));
    }
}