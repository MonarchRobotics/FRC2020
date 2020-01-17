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

//TODO: find out real motor ports

//Set all ports here
public final class Constants {
    private static final int shooterPort = 1;
    private static final int leftWheelPort1 = 6;//TODO: CHANGE BACK TO 2
    private static final int leftWheelPort2 = 8;
    private static final int rightWheelPort1 = 3;
    private static final int rightWheelPort2 = 7;

    private static final int ballintake = 4;
    // May need to modify based on what people decide for balls
    private static final int internalmanipulation = 5;

    public static int getShooterPort() {
        return shooterPort;
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
        return ballintake;
    }
    public static int getInternalManipulation()
    {
        return internalmanipulation;
    }
}
