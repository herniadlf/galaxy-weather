package model.galaxy;

import model.galaxy.movement.GalaxyPosition;

public class OrbitalCenter extends GalaxyComponent{
    public OrbitalCenter(){
        super(new GalaxyPosition(0.0,0.0));
    }
}
