package model.galaxy.planet;


import model.galaxy.OrbitalComponent;
import model.galaxy.movement.GalaxyPosition;
import model.galaxy.movement.OrbitalSpeed;

import javax.validation.constraints.NotNull;

public class Planet extends OrbitalComponent {
    private final OrbitalSpeed speed;

    public Planet(@NotNull GalaxyPosition initPosition, @NotNull OrbitalSpeed _speed){
        super(initPosition);
        speed = _speed;
    }
    public Planet(String name, @NotNull GalaxyPosition initPosition, @NotNull OrbitalSpeed _speed){
        super(name, initPosition);
        speed = _speed;
    }

    @Override
    public OrbitalSpeed getSpeed() {
        return speed;
    }

    /**
     * @return the new position after a movement using planet orbitalSpeed
     */
    @Override
    public GalaxyPosition move() {
        final double newAngle;
        switch (speed.orientation){
            case CLOCKWISE:
                newAngle = angle - Math.toRadians(speed.rate);
                break;
            case COUNTER_CLOCKWISE:
                newAngle = angle + Math.toRadians(speed.rate);
                break;
            default:
                throw new RuntimeException("Undefined speed orientation");
        }
        updatePosition(newAngle);
        return position;
    }

    @Override
    public OrbitalComponent copy() {
        final GalaxyPosition newPosition = new GalaxyPosition(position.x, position.y);
        final OrbitalSpeed newSpeed = new OrbitalSpeed(speed.orientation, speed.rate);
        return new Planet(name, newPosition, newSpeed);
    }
}
