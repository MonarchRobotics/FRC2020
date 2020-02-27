package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.MotorControlPID;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;


public class SpinToPort extends CommandBase {
    private final Drivetrain drivetrain;
    private double spinSpeed;
    private MotorControlPID spinControl;
    private boolean isFinished;

    public SpinToPort(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
        isFinished = false;
    }

    /**
     * The initial subroutine of a command.  Called once when the command is initially scheduled.
     */
    @Override
    public void initialize() {
        spinSpeed = 0;
        spinControl = new MotorControlPID(160,1.0,0.1,0.0001);
    }

    /**
     * The main body of a command.  Called repeatedly while the command is scheduled.
     * (That is, it is called repeatedly until {@link #isFinished()}) returns true.)
     */
    @Override
    public void execute() {
        double[] coords = Robot.getTargetCenterCoordinates();
        if(coords[0]==-1){
            if(drivetrain.getAutoSwitch().get()){
                spinSpeed = 0.5;
            }
            else {
                spinSpeed = -0.5;
            }
        }
        else if(coords[0]!= 160){
            spinSpeed = spinControl.getSpeed(coords[0]);
//                    if(drivetrain.getAutoSwitch().get()){
//                        double speed = (160-coords[0])/640;
//                        if(speed>0){speed=1;}
//                        spinSpeed = speed;
//                    }
//                    else if(!drivetrain.getAutoSwitch().get()){
//                        double speed = (coords[0]-160)/640;
//                        if(speed>0){speed=1;}
//                        spinSpeed = -speed;
//                    }
        }
        else {
            isFinished = true;
        }
        System.out.println("spinSpeed: "+spinSpeed);
        drivetrain.ldrive(-spinSpeed);
        drivetrain.rdrive(spinSpeed);
    }

    /**
     * <p>
     * Returns whether this command has finished. Once a command finishes -- indicated by
     * this method returning true -- the scheduler will call its {@link #end(boolean)} method.
     * </p><p>
     * Returning false will result in the command never ending automatically. It may still be
     * cancelled manually or interrupted by another command. Hard coding this command to always
     * return true will result in the command executing once and finishing immediately. It is
     * recommended to use * {@link edu.wpi.first.wpilibj2.command.InstantCommand InstantCommand}
     * for such an operation.
     * </p>
     *
     * @return whether this command has finished.
     */
    @Override
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * The action to take when the command ends. Called when either the command
     * finishes normally -- that is it is called when {@link #isFinished()} returns
     * true -- or when  it is interrupted/canceled. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the command.
     *
     * @param interrupted whether the command was interrupted/canceled
     */
    @Override
    public void end(boolean interrupted) {
        drivetrain.rdrive(0);
        drivetrain.ldrive(0);
    }
}
