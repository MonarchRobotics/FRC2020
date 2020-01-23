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
//import com.ctre.phoenix.motorcontrol.can.BaseMotorController.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.DriveTank;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

// Talons
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;

// Victor spx

/**
 * It's the drivetrain. I don't know what you expected.
 */
public class Drivetrain extends SubsystemBase {
    // Creates the differential drive.
    protected DifferentialDrive drive;



    public VictorSPX left1, left2, right1, right2;
    public Drivetrain(int Left1, int Left2, int Right1, int Right2) {
        //Initialises each of the 4 motors for both sides of the drivetrain.
        left1 = new VictorSPX(Left1);
        left2 = new VictorSPX(Left2);
        right1 = new VictorSPX(Right1);
        right2 = new VictorSPX(Right2);

        setDefaultCommand(new DriveTank(this));
    }
    
    public void rdrive(double speed){
        right1.set(ControlMode.PercentOutput, -speed);
        right2.set(ControlMode.PercentOutput, -speed);
    }
    public void ldrive(double speed){
        left1.set(ControlMode.PercentOutput, -speed);
        left2.set(ControlMode.PercentOutput, -speed);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
