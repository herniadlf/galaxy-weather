package model.galaxy.planet;

import model.galaxy.OrbitalComponentException;
import model.galaxy.movement.GalaxyPosition;
import model.galaxy.movement.OrbitalSpeed;

import javax.validation.constraints.NotNull;

public class DummyPlanetImpl extends Planet {
    public DummyPlanetImpl(@NotNull GalaxyPosition initPosition, @NotNull OrbitalSpeed _speed) throws OrbitalComponentException {
        super(initPosition, _speed);
    }
}
