package model.galaxy;

import model.galaxy.movement.GalaxyPosition;
import model.galaxy.movement.OrbitalSpeed;
import model.galaxy.planet.DummyPlanetImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class GalaxyTest {

    private final OrbitalSpeed speed1 = new OrbitalSpeed(OrbitalSpeed.ORIENTATION.CLOCKWISE, 90);
    private final GalaxyPosition planet1Position= new GalaxyPosition(0.0, 500.0);

    @Test public void galaxyBuilderDefaultCreationTest(){
        final Galaxy.GalaxyBuilder builder = new Galaxy.GalaxyBuilder();
        final OrbitalCenter defaultCenter = new OrbitalCenter();
        final Galaxy galaxy = builder.withCenter(defaultCenter).create();
        Assert.assertEquals(defaultCenter.getPosition(), galaxy.getCenter().getPosition());
    }
    private Galaxy createGalaxyWithOneComponent(){
        final Galaxy.GalaxyBuilder builder = new Galaxy.GalaxyBuilder();
        builder.withCenter(new OrbitalCenter());
        OrbitalComponent component = null;
        try{
            component = new DummyPlanetImpl(planet1Position, speed1);
        } catch (final Exception ignore){}
        final ArrayList<OrbitalComponent> components = new ArrayList<>();
        components.add(component);
        builder.withComponents(components);
        return builder.create();
    }

    @Test public void galaxyCreatedWithComponentsTest(){
        final Galaxy galaxy = createGalaxyWithOneComponent();
        final OrbitalComponent orbitalComponent = galaxy.getComponents().get(0);
        Assert.assertEquals(orbitalComponent.getPosition(), planet1Position);
        Assert.assertEquals(orbitalComponent.getSpeed(), speed1);
    }

    @Test public void galaxyFinishDayTest(){
        final Galaxy galaxy = createGalaxyWithOneComponent();
        galaxy.finishDay();
        final OrbitalComponent orbitalComponent = galaxy.getComponents().get(0);
        final GalaxyPosition newPosition = new GalaxyPosition(500.0, 0.0);
        Assert.assertEquals(orbitalComponent.getPosition(), newPosition);
    }

}