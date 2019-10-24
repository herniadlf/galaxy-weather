package model;


import javax.validation.constraints.NotNull;

public class GalaxyComponent {
    protected Position position;

    GalaxyComponent(Position initPosition){
        position = initPosition;
    }

    public @NotNull Position getPosition() {
        return position;
    }

}
