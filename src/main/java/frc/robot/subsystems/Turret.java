/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.Shoot;

/**
 * The ball shooter.
 */
public class Turret extends SubsystemBase {

    private TalonSRX wheel;
    private TalonSRX inputWheel;

    public Turret(int wheelPort, int inputWheelPort) {
        wheel = new TalonSRX(wheelPort);
        // wheel2= new TalonSRX(wheel2Port);

        inputWheel = new TalonSRX(inputWheelPort);
        
        wheel.setNeutralMode(NeutralMode.Brake);
        // wheel2.setNeutralMode(NeutralMode.Brake);

        
        setDefaultCommand(new Shoot(this));
    }

    public TalonSRX getWheelMotor() { return wheel;}

    // public TalonSRX getWheel2Motor() { return wheel2;}

    public TalonSRX getInputWheelMotor() { return inputWheel;}
    // @Override
    // public void initDefaultCommand(){
    //   setDefaultCommand(new Shoot());
    // }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
