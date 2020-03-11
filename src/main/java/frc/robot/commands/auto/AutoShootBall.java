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

import java.util.Arrays;

public class AutoShootBall extends CommandBase {

    private final Turret turret;
    private Timer timer;
    private Drivetrain driveTrain;

    private Robot robot;

    private MotorControlPID motorControl;
    private double[] errors;
    private boolean shooting;

    /**
     * Constructor for AutoShootBall
     * @param turret the Turret Subsystem {@link Turret} so that we cna shoot balls
     */
    public AutoShootBall(Turret turret, double rpmTarget){
        this.turret = turret;
        timer = new Timer();

        addRequirements(turret);
        motorControl = new MotorControlPID(rpmTarget,1.0,1.0,0.1,0.001);
        errors = new double[5];
        Arrays.fill(errors,100);;
        shooting = false;
    }


    @Override
    public void initialize() {
        timer.reset();
        motorControl.reset();

        errors = new double[5];
        Arrays.fill(errors,100);
        shooting = false;
    }

    
    /**
     * Runs the ball shooting system for a set amount of time till the current 3 balls have most likely been shot    
     *  
     */
    @Override
    public void execute() {
        double leftSpeed = motorControl.getSpeed(turret.getEncoderLeftRate());
            // double rightSpeed = motorControlRight.getSpeed(turret.getEncoderRightRate());
            // System.out.println("RPM:"+turret.getEncoderLeftRate());
        errors[0] = errors[1];
        errors[1] = errors[2];
        errors[2] = errors[3];
        errors[3] = errors[4];
        errors[4] = Math.abs(motorControl.getPreviousE());
        double average = (errors[0]+errors[1]+errors[2]+errors[3]+errors[4])/5;
        turret.spinMotors(leftSpeed,leftSpeed);
        if(average<1.5 && Robot.canShootAuto && !shooting){
            shooting = true;
            timer.start();
        }
        if(shooting){
            turret.getInputWheelMotor().set(ControlMode.PercentOutput,1.0);
            // System.out.println("Timer: "+timer.get());
        }
        
    }


    /**
     * Called once the command ends or is interrupted.
     * Stops all of the motors from spinning
     */
    @Override
    public void end(boolean interrupted) {
        turret.spinMotors(0.0, 0.0);
        turret.getInputWheelMotor().set(ControlMode.PercentOutput,0.0);
        
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return timer.get()>1.0;
    }
}
