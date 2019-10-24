package model.galaxy;

import model.galaxy.movement.GalaxyPosition;

public class OrbitalCenter extends GalaxyComponent{
    OrbitalCenter(){
        super(new GalaxyPosition(0.0,0.0));
    }
}
