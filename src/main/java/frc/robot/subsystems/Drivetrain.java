/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.robot.commands.DriveTank;
import edu.wpi.first.wpilibj.*;

public class Drivetrain extends SubsystemBase {
    protected DifferentialDrive drive;

    WPI_TalonSRX talonL1 = new WPI_TalonSRX();


    public TalonSRX left, right;
    public Drivetrain(int left, int right) {

    }

    // @Override
    // public void initDefaultCommand(){
    //   setDefaultCommand(new DriveTank());
    // }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
