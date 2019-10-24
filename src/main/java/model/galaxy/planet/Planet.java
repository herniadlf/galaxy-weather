package model.galaxy.planet;


import model.galaxy.OrbitalComponent;
import model.galaxy.movement.OrbitalSpeed;
import model.galaxy.movement.GalaxyPosition;

import javax.validation.constraints.NotNull;

public abstract class Planet extends OrbitalComponent {
    private final OrbitalSpeed speed;

    protected Planet(@NotNull GalaxyPosition initPosition, @NotNull OrbitalSpeed _speed) {
        super(initPosition);
        speed = _speed;
    }

    @Override
    public OrbitalSpeed getSpeed() {
        return speed;
    }

    @Override
    public GalaxyPosition move() {
        final GalaxyPosition newPosition = new GalaxyPosition(500.0, 0.0);
        position = newPosition;
        return newPosition;
    }
}
