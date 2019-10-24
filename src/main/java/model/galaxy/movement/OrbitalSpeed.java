package model.galaxy.movement;


import javax.validation.constraints.NotNull;

public class OrbitalSpeed {
    public enum ORIENTATION { CLOCKWISE, COUNTER_CLOCKWISE }
    public final ORIENTATION orientation;
    public final Integer rate;

    public OrbitalSpeed(ORIENTATION _orientation, @NotNull Integer _rate){
        orientation = _orientation;
        rate = _rate;
    }
}
