package model.galaxy.planet;

import model.galaxy.movement.GalaxyPosition;
import model.galaxy.movement.OrbitalSpeed;

import javax.validation.constraints.NotNull;

public class DummyPlanetImpl extends Planet {
    protected DummyPlanetImpl(@NotNull GalaxyPosition initPosition, @NotNull OrbitalSpeed _speed) {
        super(initPosition, _speed);
    }
}
