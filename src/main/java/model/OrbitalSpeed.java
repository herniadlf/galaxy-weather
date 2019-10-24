package model;


import javax.validation.constraints.NotNull;

public class OrbitalSpeed {
    enum ORIENTATION { CLOCKWISE, COUNTER_CLOCKWISE }
    private ORIENTATION orientation;
    private Integer amount;

    public OrbitalSpeed(ORIENTATION _orientation, @NotNull Integer _amount){
        orientation = _orientation;
        amount = _amount;
    }
}
