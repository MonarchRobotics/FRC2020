/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants;
import frc.robot.MotorControlPID;
import frc.robot.commands.DriveTank;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;


/**
 * This code is for the subsystem drivetrain, includes 4 motors.
 */
public class Drivetrain extends SubsystemBase {

    public VictorSPX left1, left2, right1, right2;
    private Encoder encoder = new Encoder(2, 3);
    private Encoder encoderRight = new Encoder(0, 1);
    private Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
    private DigitalInput autoSwitch = new DigitalInput(9);

    private LidarLite lidar = new LidarLite(I2C.Port.kOnboard);

    /**
     * 
     * @param left1 Left motor port 1.
     * @param left2 Left motor port 2.
     * @param right1 Right motor port 1.
     * @param right2 Right motor port 2.
     */
    public Drivetrain(int left1, int left2, int right1, int right2) {
        //Initialises each of the 4 motors for both sides of the drivetrain.
        this.left1 = new VictorSPX(left1);
        this.left2 = new VictorSPX(left2);
        this.right1 = new VictorSPX(right1);
        this.right2 = new VictorSPX(right2);

        //Enables break mode for all of our motors so it doesn't destroy itself.
        this.left2.setNeutralMode(NeutralMode.Brake);
        this.left1.setNeutralMode(NeutralMode.Brake);
        this.right2.setNeutralMode(NeutralMode.Brake);
        this.right1.setNeutralMode(NeutralMode.Brake);

        //link the command to the subsystem
        setDefaultCommand(new DriveTank(this));

        gyro.calibrate();


        //set up the distance to pulse ratio for each of the two encoders.
        encoder.setDistancePerPulse(7.5*Math.PI/2048.0);//this should be 1 rotation, eventually will be converted to inches
        encoderRight.setDistancePerPulse(7.5*Math.PI/2048.0);//this should be 1 rotation, eventually will be converted to inches
    }

    /**
     *
     * @param speed Speed input from joysticks.
     * @return The speed adjusted using a sine curve.
     */
    private double speedRamp(double speed)
    {
        //return speed;

        double speedPrime;

        speedPrime = Math.sin(Math.PI/2*speed);
        return speedPrime;
    }


    public void rdrive(double speed){
        // Sets the right motors speed output.
        right1.set(ControlMode.PercentOutput, -speedRamp(speed));
        right2.set(ControlMode.PercentOutput, -speedRamp(speed));
    }
    public void ldrive(double speed){
        // Sets the left motors speed output.
        left1.set(ControlMode.PercentOutput, speedRamp(speed));
        left2.set(ControlMode.PercentOutput, speedRamp(speed));
    }

    /**
     * @return The lidar measurement in inches
     */

    public double getLidarMeasurement() {
        return lidar.getDistance() / 2.54;
    }

    //returns the left encoder (in ports 2 and 3)
    public Encoder getEncoderLeft() {return encoder;}

    //returns the right encoder (in ports 0 and 1)
    public Encoder getEncoderRight() {
        return encoderRight;
    }

    public Gyro getGyro() {
        return gyro;
    }

    public DigitalInput getAutoSwitch() {
        return autoSwitch;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run, doesn't do anything at the moment
    }
}
