package model.galaxy.movement;


import model.galaxy.Orientation;

import javax.validation.constraints.NotNull;

public class OrbitalSpeed {
    public final Orientation orientation;
    public final Integer rate;

    public OrbitalSpeed(Orientation _orientation, @NotNull Integer _rate){
        orientation = _orientation;
        rate = _rate;
    }
}
