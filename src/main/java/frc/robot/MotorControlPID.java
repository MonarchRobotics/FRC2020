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

    public MotorControlPID(double target, double maxValue, double Kp, double ceiling, double Ki, double Kd){
        this.target = target;
        this.maxValue = maxValue;
        this.ceiling = ceiling;
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        sumE = 0;
        previousE = 0;
    }

    double getSpeed(double current){//-maxValue to maxValue of how fast we want the motor to go
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
}
