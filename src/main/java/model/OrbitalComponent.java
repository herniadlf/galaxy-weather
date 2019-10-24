package model;


public abstract class OrbitalComponent extends GalaxyComponent implements OrbitalMovable{

    OrbitalComponent(Position initPosition) {
        super(initPosition);
    }
}
