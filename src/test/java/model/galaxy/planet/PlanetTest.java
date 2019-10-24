package model.galaxy.planet;


import model.galaxy.movement.GalaxyPosition;
import model.galaxy.movement.OrbitalSpeed;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlanetTest {

    private GalaxyPosition initPosition = null;
    private GalaxyPosition expectedPosition = null;
    private OrbitalSpeed speed = null;
    private DummyPlanetImpl planet = null;

    @Before public void setup(){
        initPosition = new GalaxyPosition(0.0,500.0);
        speed = new OrbitalSpeed(OrbitalSpeed.ORIENTATION.CLOCKWISE, 90);
        planet = new DummyPlanetImpl(initPosition, speed);
    }

    @Test public void move90DegreesClockWiseTest(){
        planet.move();
        expectedPosition = new GalaxyPosition(500.0, 0.0);
        Assert.assertEquals(planet.getPosition(), expectedPosition);
    }
}