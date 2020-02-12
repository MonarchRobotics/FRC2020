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

    public double getSpeed(double current){//-maxValue to maxValue of how fast we want the motor to go
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

    void reset(){
        previousE = 0;
        sumE = 0;
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
