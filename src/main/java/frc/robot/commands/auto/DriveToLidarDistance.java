package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;


public class DriveToLidarDistance extends CommandBase {
    private final Drivetrain drivetrain;
    private double distance;
    private double driveDistance;
    private double encoderInit;
    private double speed;

    /**
     * @param drivetrain The Drivetrain subsystem
     * @param distance The distance the LIDAR should read before we stop driving
     * @param speed How fast we should drive, -1 to 1
     */

    public DriveToLidarDistance(Drivetrain drivetrain, double distance, double speed) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);

        this.distance = distance;
        this.speed = speed;
        driveDistance = 0;
        encoderInit = (drivetrain.getEncoderLeft().getDistance() + drivetrain.getEncoderLeft().getDistance())/2;
    }

    @Override
    public void initialize() {
        drivetrain.ldrive(speed);
        drivetrain.rdrive(speed);
    }

    @Override
    public void execute() {
        driveDistance = (drivetrain.getEncoderLeft().getDistance() + drivetrain.getEncoderLeft().getDistance())/2 - encoderInit;

        if (drivetrain.getLidarMeasurement() > distance || driveDistance > distance)
        {
            drivetrain.ldrive(speed);
            drivetrain.rdrive(speed);
        }
        else
        {
        drivetrain.rdrive(0);
        drivetrain.ldrive(0);
        }
    }

    @Override
    public boolean isFinished() {
        //keep driving until the LIDAR reads the correct thing
        return speed>0 ? drivetrain.getLidarMeasurement()<distance : drivetrain.getLidarMeasurement()>distance;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
