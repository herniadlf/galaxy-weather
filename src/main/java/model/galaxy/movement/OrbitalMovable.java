package model.galaxy.movement;

public interface OrbitalMovable {

    OrbitalSpeed getSpeed();
    GalaxyPosition move();
}
