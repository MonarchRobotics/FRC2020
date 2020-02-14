package frc.robot.enums;

public enum ColorSensorColor {
    red,
    blue,
    green,
    yellow,
    none;

    public int toInt(){
        switch(this){
            case red:
                return 1;
            case yellow:
                return 2;
            case blue:
                return 3;
            case green:
                return 4;
            default:
                return 0;
        }
    }
}
