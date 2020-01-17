/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.WheelOfFortune;
import com.ctre.phoenix.motorcontrol.can.*;

/**
 * Colour sensor and wheel for the Control Panel.
 */
public class WheelManipulator extends SubsystemBase {
    private final I2C.Port i2cPort = I2C.Port.kOnboard;

    private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);

    private TalonSRX spinner;

    public WheelManipulator(int spinnerPort) {
        spinner = new TalonSRX(spinnerPort);
        spinner.setNeutralMode(NeutralMode.Brake);
        setDefaultCommand(new WheelOfFortune(this));
    }

    private boolean colorSensorMargin(double detected, double test){
        double error = 0.03;
        return detected<=test+error && detected>=test-error;
    }

    public int detectColor(){
        Color color = colorSensor.getColor();
        // System.out.println("R:"+Math.floor(color.red*1000)+"G:"+Math.floor(color.green*1000)+"B:"+Math.floor(color.blue*1000));
        int thisColor = 0;
        if(colorSensorMargin(color.red,0.54) && colorSensorMargin(color.green,0.35) && colorSensorMargin(color.blue,0.11)){//red
            thisColor = 1;
        }
        else if(colorSensorMargin(color.red,0.37) && colorSensorMargin(color.green,0.54) && colorSensorMargin(color.blue,0.10)){//yellow
            thisColor = 2;
        }
        else if(colorSensorMargin(color.red,0.18) && colorSensorMargin(color.green,0.53) && colorSensorMargin(color.blue,0.28)){//green
            thisColor = 4;
        }
        else if(colorSensorMargin(color.red,0.15) && colorSensorMargin(color.green,0.44) && colorSensorMargin(color.blue,0.41)){//blue
            thisColor = 3;
        }
        return thisColor;
    }

    public ColorSensorV3 getColorSensor(){
        return colorSensor;
    }

    public TalonSRX getSpinnerMotor() {
        return spinner;
    }

    // @Override
    // public void initDefaultCommand(){
    //   setDefaultCommand(new WheelOfFortune());
    // }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
