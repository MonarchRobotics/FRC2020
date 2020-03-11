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
        // new SpinInPlaceGyro(drivetrain,20,0.5);
        // new SpinInPlaceGyro(drivetrain,-20,0.5);

        addCommands(new SequentialCommandGroup(
                //get the ballsuck system out of frame perimeter at the start of the match
                new AutoInit(ballsuck,drivetrain),
                //align with the power port
                new SpinToPort(drivetrain),
                //spin up the ball shooter and shoot when once SpinToPort is done.
                new AutoShootBall(turret,28)
        ));
        addCommands(
                new SpinInPlaceGyro(drivetrain,4,0.5)
        );
        if(drivetrain.getAutoSwitch().get()) {
            //we are in front of the trench run, so go pick up the balls.
            addCommands(
                    new DriveStraight(drivetrain, ballsuck, -193, 0.25, -1),
                    new SpinInPlaceGyro(drivetrain,-20,0.5),
                    new DriveStraight(drivetrain, ballsuck, 100, 0.40, 5)
            );
            addCommands(new SequentialCommandGroup(
                    new SpinToPort(drivetrain),
                    new AutoShootBall(turret, 27.5)
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