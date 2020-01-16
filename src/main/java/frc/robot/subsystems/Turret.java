/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.Shoot;

public class Turret extends SubsystemBase {

    private TalonSRX wheel;

    public Turret(int wheelPort) {
        wheel = new TalonSRX(wheelPort);
        wheel.setNeutralMode(NeutralMode.Brake);
        setDefaultCommand(new Shoot(this));
    }

    public TalonSRX getWheelMotor() { return wheel; }

    // @Override
    // public void initDefaultCommand(){
    //   setDefaultCommand(new Shoot());
    // }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
