package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;


public class DriveToLidarDistance extends CommandBase {
    private final Drivetrain drivetrain;
    private double distance;
    private double speed;

    public DriveToLidarDistance(Drivetrain drivetrain, double distance, double speed) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);

        this.distance = distance;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        drivetrain.ldrive(speed);
        drivetrain.rdrive(speed);
    }

    @Override
    public void execute() {
        drivetrain.rdrive(0);
        drivetrain.ldrive(0);
    }

    @Override
    public boolean isFinished() {
        return speed>0 ? drivetrain.getLidarMeasurement()<distance : drivetrain.getLidarMeasurement()>distance;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
