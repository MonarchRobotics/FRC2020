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
import frc.robot.ColorSensorColor;
import frc.robot.commands.WheelOfFortune;
import com.ctre.phoenix.motorcontrol.can.*;

/**
 * Colour sensor and wheel for the Control Panel.
 */
public class WheelManipulator extends SubsystemBase {
    private final I2C.Port i2cPort = I2C.Port.kOnboard; //Port on the RIO for colour sensor.
    private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);

    private TalonSRX spinner;

    /**
     * @param spinnerPort Port for wheel motor.
     */
    public WheelManipulator(int spinnerPort) {
        spinner = new TalonSRX(spinnerPort);
        spinner.setNeutralMode(NeutralMode.Brake);
        setDefaultCommand(new WheelOfFortune(this));
    }

    /**
     * @param detected Value detected by sensor.
     * @param accepted Accepted value for colour.
     * @return if detected matches accepted within small margin of error.
     */
    private boolean colorSensorMargin(double detected, double accepted){
        double error = 0.03;
        return detected<=accepted+error && detected>=accepted-error;
    }

    /**
     * @return Detected colour as an integer. 0 = red, 1 = yellow, 2 = green, 3 = blue.
     */
    public ColorSensorColor detectColor(){
        Color color = colorSensor.getColor();
        ColorSensorColor thisColor = ColorSensorColor.none;
        if(colorSensorMargin(color.red,0.54) && colorSensorMargin(color.green,0.35) && colorSensorMargin(color.blue,0.11)){//red
            thisColor = ColorSensorColor.red;
        }
        else if(colorSensorMargin(color.red,0.37) && colorSensorMargin(color.green,0.54) && colorSensorMargin(color.blue,0.10)){//yellow
            thisColor = ColorSensorColor.yellow;
        }
        else if(colorSensorMargin(color.red,0.18) && colorSensorMargin(color.green,0.53) && colorSensorMargin(color.blue,0.28)){//green
            thisColor = ColorSensorColor.green;
        }
        else if(colorSensorMargin(color.red,0.15) && colorSensorMargin(color.green,0.44) && colorSensorMargin(color.blue,0.41)){//blue
            thisColor = ColorSensorColor.blue;
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
