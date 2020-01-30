package frc.robot.commands.auto;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;

public class AutoShootBall extends CommandBase {

    private final Turret subsystem;
    private Timer timer;


    public AutoShootBall(Turret turret){
        this.subsystem = turret;
        timer = new Timer();

        addRequirements(subsystem);
    }


    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if(timer.get()>0.4){//wait for the shooting wheels to start up, then start letting balls into the shooter
            subsystem.getInputWheelMotor().set(ControlMode.PercentOutput,1);
        }

        subsystem.getWheel2Motor().set(ControlMode.PercentOutput,1);
        subsystem.getWheelMotor().set(ControlMode.PercentOutput,1);

    }



    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        subsystem.getInputWheelMotor().set(ControlMode.PercentOutput,0);
        subsystem.getWheel2Motor().set(ControlMode.PercentOutput,0);
        subsystem.getWheelMotor().set(ControlMode.PercentOutput,0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return timer.get()>3.0;
    }
}
