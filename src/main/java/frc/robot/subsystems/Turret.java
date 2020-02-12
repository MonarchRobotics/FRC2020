/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.Shoot;

/**
 * The ball shooter.
 */
public class Turret extends SubsystemBase {

    private TalonSRX rightWheel;
    private TalonSRX leftWheel;
    private TalonSRX inputWheel;
    private Encoder encoderLeft = new Encoder(6, 7);
    private Encoder encoderRight = new Encoder(4, 5);

    public Turret(int leftWheelPort, int rightWheelPort, int inputWheelPort) {
        leftWheel = new TalonSRX(leftWheelPort);
        rightWheel = new TalonSRX(rightWheelPort);

        inputWheel = new TalonSRX(inputWheelPort);
        
        leftWheel.setNeutralMode(NeutralMode.Brake);
        rightWheel.setNeutralMode(NeutralMode.Brake);

        
        setDefaultCommand(new Shoot(this));
        encoderLeft.setDistancePerPulse(1/2048.0);
        encoderRight.setDistancePerPulse(1/2048.0);
    }

    public TalonSRX getInputWheelMotor() { return inputWheel;}

    public double getEncoderLeftRate(){
        return encoderLeft.getRate();
    }

    public double getEncoderRightRate(){
        return encoderRight.getRate();
    }

    public void spinMotors(double leftSpeed, double rightSpeed){
        leftWheel.set(ControlMode.PercentOutput,-leftSpeed);
        rightWheel.set(ControlMode.PercentOutput,rightSpeed);
    }
    // @Override
    // public void initDefaultCommand(){
    //   setDefaultCommand(new Shoot());
    // }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
