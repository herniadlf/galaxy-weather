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
}
