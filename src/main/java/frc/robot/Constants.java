/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */


//Set all ports here
public final class Constants {

    private static final int shooterPort1 = 7;
    private static final int shooterPort2 = 8;
    private static final int shooterInputPort = 9;

    private static final int leftWheelPort1 = 3;
    private static final int leftWheelPort2 = 4;
    private static final int rightWheelPort1 = 1;
    private static final int rightWheelPort2 = 2;

    private static final int wheelOfFortunePort = 10;

    private static final int ballIntakePort = 5;
    private static final int internalManipulation = 6;

    private static final double distanceAuto = 123.91;
    private static final double rotationAuto = 1.6; //

    //Constants for Deadzone
    private static final double deadZone = 0.05;
    

    public static int getShooterPort1() {
        return shooterPort1;
    }
    public static int getShooterPort2() {
        return shooterPort2;

    }
    public static int getShooterInputPort()
    {
        return shooterInputPort;
    }
    public static int getLeftWheelPort1() {
        return leftWheelPort1;
    }
    public static int getLeftWheelPort2() {
        return leftWheelPort2;
    }
    public static int getRightWheelPort1() {
        return rightWheelPort1;
    }
    public static int getRightWheelPort2() {
        return rightWheelPort2;
    }

    public static int getBallIntake()
    {
        return ballIntakePort;
    }
    public static int getInternalManipulation()
    {
        return internalManipulation;
    }

    public static int getWheelOfFortunePort() {
        return wheelOfFortunePort;
    }

    public static double getDeadZone(){ return deadZone;}

    public static double getDistanceAuto() {
        return distanceAuto;
    }
    public static double getRotationAuto() {
        return rotationAuto;
    }

    public static double getTimeToTravelDistance(double inches, double speed){
        return inches / getDistanceAuto() / speed;
    }
}
