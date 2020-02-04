/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.*;
//import com.ctre.phoenix.motorcontrol.can.*;
//import edu.wpi.first.wpilibj.drive.*;
//import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;

//import com.ctre.phoenix.motorcontrol.can.BaseMotorController.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.DriveTank;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

// Talons
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;

// Victor spx

/**
 * This code is for the subsystem drivetrain, includes 4 motors.
 */
public class Drivetrain extends SubsystemBase {

    public VictorSPX left1, left2, right1, right2;
    private Encoder encoderLeft = new Encoder(2, 3);
    private Encoder encoderRight = new Encoder(0, 1);
    private Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);


    public Drivetrain(int Left1, int Left2, int Right1, int Right2) {
        //Initialises each of the 4 motors for both sides of the drivetrain.
        left1 = new VictorSPX(Left1);
        left2 = new VictorSPX(Left2);
        right1 = new VictorSPX(Right1);
        right2 = new VictorSPX(Right2);

        //Enables break mode for all of our motors so it doesn't destroy itself.
        left2.setNeutralMode(NeutralMode.Brake);
        left1.setNeutralMode(NeutralMode.Brake);
        right2.setNeutralMode(NeutralMode.Brake);
        right1.setNeutralMode(NeutralMode.Brake);

        //link the command to the subsystem
        setDefaultCommand(new DriveTank(this));

        gyro.calibrate();

        //set up the distance to pulse ratio for each of the two encoders.
        encoderLeft.setDistancePerPulse(1.0/2048.0);//this should be 1 rotation, eventually will be converted to inches
        encoderRight.setDistancePerPulse(1.0/2048.0);//this should be 1 rotation, eventually will be converted to inches
    }
    
    public void rdrive(double speed){
        // Sets the right motors speed %output.
        right1.set(ControlMode.PercentOutput, -speed);
        right2.set(ControlMode.PercentOutput, -speed);
    }
    public void ldrive(double speed){
        // Sets the left motors speed %output.
        left1.set(ControlMode.PercentOutput, speed);
        left2.set(ControlMode.PercentOutput, speed);
    }

    //returns the left encoder (in ports 2 and 3)
    public Encoder getEncoderLeft() {return encoderLeft;}

    //returns the right encoder (in ports 0 and 1)
    public Encoder getEncoderRight() {
        return encoderRight;
    }

    public Gyro getGyro() {
        return gyro;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run, doesn't do anything at the moment
    }
}
