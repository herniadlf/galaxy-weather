package model;


import javax.validation.constraints.NotNull;

public class Planet extends OrbitalComponent{
    private final OrbitalSpeed speed;

    Planet(@NotNull Position initPosition, @NotNull OrbitalSpeed _speed) {
        super(initPosition);
        speed = _speed;
    }

    @Override
    public OrbitalSpeed getSpeed() {
        return speed;
    }

    @Override
    public Position move() {
        throw new RuntimeException("To be implemented");
    }
}
