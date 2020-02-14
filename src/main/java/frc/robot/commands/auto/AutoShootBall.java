package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Drivetrain;
import frc.robot.MotorControlPID;
import frc.robot.Robot;

public class AutoShootBall extends CommandBase {

    private final Turret turret;
    private Timer timer;
    private Drivetrain driveTrain;

    private Robot robot;

    private MotorControlPID motorControlLeft;
    private MotorControlPID motorControlRight;

    private double targetSpinSpeed = 100;


    /**
     * Constructor for AutoShootBall
     * @param turret the Turret Subsystem {@link Turret} so that we cna shoot balls
     */
    public AutoShootBall(Turret turret, Drivetrain driveTrain, Robot robot){
        this.turret = turret;
        this.driveTrain = driveTrain;
        this.robot = robot;
        timer = new Timer();

        addRequirements(turret);
        motorControlLeft = new MotorControlPID(targetSpinSpeed,1.0,1.0,1.0,1.0);
        motorControlRight = new MotorControlPID(targetSpinSpeed,1.0,1.0,1.0,1.0);
    }


    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }

    
    /**
     * Runs the ball shooting system for a set amount of time till the current 3 balls have most likely been shot    
     *  
     */
    @Override
    public void execute() {
        double leftSpeed = motorControlLeft.getSpeed(turret.getEncoderLeftRate());
        double rightSpeed = motorControlRight.getSpeed(turret.getEncoderRightRate());
        turret.spinMotors(leftSpeed,rightSpeed);
        
        // Checks for the amount of time it takes to input and shoot balls, then stops the input morot
        if(timer.get() > 0.4)
        {
            turret.runInput();
        }
        else if (timer.get() > 3)
        {
            turret.stopInput();
            turret.spinMotors(0, 0);
        }
        robot.position[0] = driveTrain.getGyro().getAngle();
        robot.position[1] = driveTrain.getLidarMeasurement();
        
    }


    /**
     * Called once the command ends or is interrupted.
     * Stops all of the motors from spinning
     */
    @Override
    public void end(boolean interrupted) {
        
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return timer.get()>3.0;
    }
}
