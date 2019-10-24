package model.galaxy;


import model.galaxy.movement.GalaxyPosition;

import javax.validation.constraints.NotNull;

public class GalaxyComponent {
    protected GalaxyPosition position;

    GalaxyComponent(GalaxyPosition initPosition){
        position = initPosition;
    }

    public @NotNull GalaxyPosition getPosition() {
        return position;
    }

}
