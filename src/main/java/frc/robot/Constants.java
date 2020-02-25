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

    // Ports
    // Ball shooter ports (Talon SRX)
    private static final int shooterPort1 = 7;
    private static final int shooterPort2 = 8;
    private static final int shooterInputPort = 9;

    // Drive wheel ports (Victor SPX)
    private static final int leftWheelPort1 = 3;
    private static final int leftWheelPort2 = 4;
    private static final int rightWheelPort1 = 1;
    private static final int rightWheelPort2 = 2;

    // Wheel of fortune spinner wheel port (Relay)
    private static final int wheelOfFortunePort = 0;

    // Ball intake and roller coaster ports (
    private static final int ballIntakePort = 8;
    // Ball intake release solenoid for the start of the match
    private static final int intakeRelease = 11;
    private static final int internalManipulation = 10;

    private static final double distanceAuto = 125.0;//in/sec at 100% power
    private static final double rotationAuto = 16; //rotations/sec at 100% power

    // Pulse time for solenoid for intake release
    private static final double pulseTimer = 0.1;

    //Constants for Deadzone
    private static final double deadZone = 0.1;

    private static final int openChannel = 1;
    private static final int closeChannel = 3;

    private static final double spinCircumference = Math.PI* 28;
    

    
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
    public static int getintakeRelease()
    {
        return intakeRelease;
    }
    public static int getInternalManipulation()
    {
        return internalManipulation;
    }
    // Pulse time 
    public static double getpulseTimer()
    {
        return pulseTimer;
    }

    public static int getWheelOfFortunePort() {
        return wheelOfFortunePort;
    }

    // Get dead zone constants
    public static double getDeadZone(){ 
        return deadZone;
    }

    // Get auto constants
    public static double getDistanceAuto() {
        return distanceAuto;
    }
    public static double getRotationAuto() {
        return rotationAuto;
    }

    public static double getTimeToTravelDistance(double inches, double speed){
        return (inches/(1.67+0.914*Math.log(speed))+7.89)/74.4;
    }

    public static double getDistanceFromTime(double time, double speed){
        return (74.4*time-7.89)*(1.67+0.914*Math.log(speed));
    }

    public static double getTimeToRotate(double radians, double speed) {
        return radians / (getRotationAuto()*2*Math.PI) / speed;
    }

    public static double getSpinCircumference() {
        return spinCircumference;
    }

    /**
     * @return the openchannel
     */
    public static int getOpenChannel() {
        return openChannel;
    }

    /**
     * @return the closechannel
     */
    public static int getCloseChannel() {
        return closeChannel;
    }
}
