package model.galaxy;


import model.galaxy.movement.OrbitalMovable;
import model.galaxy.movement.GalaxyPosition;

public abstract class OrbitalComponent extends GalaxyComponent implements OrbitalMovable {

    protected OrbitalComponent(GalaxyPosition initPosition) {
        super(initPosition);
    }
}
