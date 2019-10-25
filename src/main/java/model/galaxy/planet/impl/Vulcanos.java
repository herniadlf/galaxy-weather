package model.galaxy.planet.impl;

import model.galaxy.OrbitalComponentException;
import model.galaxy.movement.GalaxyPosition;
import model.galaxy.movement.OrbitalSpeed;
import model.galaxy.planet.Planet;

import javax.validation.constraints.NotNull;

public class Vulcanos extends Planet {
    protected Vulcanos(@NotNull GalaxyPosition initPosition, @NotNull OrbitalSpeed _speed) throws OrbitalComponentException {
        super(initPosition, _speed);
    }
}
