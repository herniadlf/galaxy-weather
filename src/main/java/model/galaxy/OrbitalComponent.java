package model.galaxy;


import model.galaxy.movement.OrbitalMovable;
import model.galaxy.movement.GalaxyPosition;

import javax.validation.constraints.NotNull;

public abstract class OrbitalComponent extends GalaxyComponent implements OrbitalMovable {
    protected final Double radius;
    protected Double angle;

    protected OrbitalComponent(GalaxyPosition initPosition) throws OrbitalComponentException {
        super(initPosition);
        if (initPosition.x == 0 && initPosition.y == 0) {
            throw new OrbitalComponentException("Orbital position cannot be (0,0)");
        }
        radius = Math.hypot(position.x, position.y);
        angle = Math.asin(position.y / radius);
    }

    protected void updatePosition(@NotNull Double newAngle){
        angle = newAngle;
        final Double newXPosition = Math.cos(angle) * radius;
        final Double newYPosition = Math.sin(angle) * radius;
        position = new GalaxyPosition(newXPosition, newYPosition);
    }
}
