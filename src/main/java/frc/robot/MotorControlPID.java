package frc.robot;

public class MotorControlPID {
    double target;
    double Kp;
    double Ki;
    double Kd;
    double sumE;
    double previousE;
    double maxValue;
    double ceiling;

    /**
     * @param target The target reading of whatever variable we want to target (encoder revolution rate, camera position, etc)
     * @param maxValue The maximum value of the function. This speed calculation from the PID calculation is multiplied by maxSpeed. default is 1.
     * @param ceiling If the speed is above this value, set the speed to this value
     * @param Kp Constant for P
     * @param Ki Constant for I
     * @param Kd Constant for D
     * */

    public MotorControlPID(double target, double maxValue, double ceiling, double Kp, double Ki, double Kd){
        this.target = target;
        this.maxValue = maxValue;
        this.ceiling = ceiling;
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        sumE = 0;
        previousE = 0;
    }

    public MotorControlPID(double target, double maxValue, double ceiling, double Kp, double Ki){
        this(target,maxValue,ceiling,Kp,Ki,0);
    }

    public MotorControlPID(double target, double maxValue, double ceiling, double Kp){
        this(target,maxValue,ceiling,Kp,0.0,0.0);
    }

    /**
    * @param current the current reading of whatever variable we are trying to reach (encoder revolution rate, camera position, etc)
    * @return The speed the motor controller should give to the motor.
    * The value returned will be between {@link #maxValue} and - {@link #maxValue}, unless the value is above {@link #ceiling}, in which case the value will be {@link #ceiling}
    * */
    public double getSpeed(double current){
        //do some calculations to determine how fast the motor will go.
        //let e = vt -vc;
        //vs = (Kp * e) + (Ki * sum(e)) + (Kd*delta(e));

        double e = (target - current);
        double deltaE = previousE - e;
        sumE += e;
        double speed = Kp * e + Ki * sumE + Kd * deltaE;
        previousE = e;
        double adjustedSpeed = speed * maxValue;
        if(adjustedSpeed>0 && adjustedSpeed>ceiling){
            adjustedSpeed = ceiling;
        }
        else if(adjustedSpeed<0 && adjustedSpeed<-ceiling){
            adjustedSpeed = -ceiling;
        }
        return adjustedSpeed;
    }


    /**
     * Resets the summation and delta calculations (generally for when we stop and need to speed up again)
     * */
    public void reset(){
        previousE = 0;
        sumE = 0;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public void setKd(double kd) {
        Kd = kd;
    }

    public void setKi(double ki) {
        Ki = ki;
    }

    public void setKp(double kp) {
        Kp = kp;
    }

    public double getKd() {
        return Kd;
    }

    public double getKi() {
        return Ki;
    }

    public double getKp() {
        return Kp;
    }
}
