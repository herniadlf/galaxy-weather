package model.galaxy;


import model.galaxy.movement.OrbitalMovable;
import model.galaxy.movement.GalaxyPosition;

import javax.validation.constraints.NotNull;

/**
 * Every OrbitalComponent is around a center, interpreted as a x-y plain so it needs to declare:
 * - radius (distance from center)
 * - angle
 */
public abstract class OrbitalComponent extends GalaxyComponent implements OrbitalMovable {
    protected final Double radius;
    protected Double angle;

    /**
     * If X and Y position are both 0, it means no distance from center.
     * We have to fail otherwise the Math Op's will fail at some point
     */
    protected OrbitalComponent(String name, GalaxyPosition initPosition){
        super(name, initPosition);
        if (initPosition.x == 0 && initPosition.y == 0) {
            throw new OrbitalComponentException("Orbital position cannot be (0,0)");
        }
        radius = Math.hypot(position.x, position.y);
        angle = Math.asin(position.y / radius);
    }

    protected OrbitalComponent(GalaxyPosition initPosition){
        this("", initPosition);
    }

    /**
     * @param newAngle after an angular movement, so here we calculate the new position
     */
    protected void updatePosition(@NotNull Double newAngle){
        angle = newAngle;
        final Double newXPosition = Math.cos(angle) * radius;
        final Double newYPosition = Math.sin(angle) * radius;
        position = new GalaxyPosition(newXPosition, newYPosition);
    }

    public abstract OrbitalComponent copy();
}
