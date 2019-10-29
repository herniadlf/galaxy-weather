package model.galaxy;


import model.galaxy.movement.GalaxyPosition;

import javax.validation.constraints.NotNull;

public class GalaxyComponent {
    protected String name;
    protected GalaxyPosition position;

    GalaxyComponent(String _name, GalaxyPosition initPosition){
        name = _name;
        position = initPosition;
    }

    GalaxyComponent(GalaxyPosition initPosition){
        this("", initPosition);
    }

    public @NotNull GalaxyPosition getPosition() {
        return position;
    }

    public @NotNull String getName() {
        return name;
    }
}
