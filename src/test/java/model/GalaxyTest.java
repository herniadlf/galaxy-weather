package model;

import org.junit.Assert;
import org.junit.Test;


public class GalaxyTest {

    @Test public void galaxyDefaultCreation(){
        final Galaxy galaxy = new Galaxy();
        final OrbitalCenter defaultCenter = new OrbitalCenter();
        Assert.assertEquals(defaultCenter.getPosition(), galaxy.getCenter().getPosition());
    }
}