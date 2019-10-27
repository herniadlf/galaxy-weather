package model.galaxy;

import model.galaxy.movement.GalaxyPosition;
import model.galaxy.movement.OrbitalSpeed;
import model.galaxy.planet.Planet;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class GalaxyTest {

    private final OrbitalSpeed speed1 = new OrbitalSpeed(Orientation.CLOCKWISE, 90);
    private final GalaxyPosition planet1Position= new GalaxyPosition(0.0, 500.0);
    private final OrbitalSpeed speed2 = new OrbitalSpeed(Orientation.CLOCKWISE, 90);
    private final GalaxyPosition planet2Position= new GalaxyPosition(500.0, 0.0);
    private final OrbitalSpeed speed3 = new OrbitalSpeed(Orientation.CLOCKWISE, 90);
    private final GalaxyPosition planet3Position= new GalaxyPosition(0.0, -500.0);

    @Test public void galaxyBuilderDefaultCreationTest(){
        final Galaxy galaxy = createGalaxyWithThreeComponent();
        final OrbitalCenter defaultCenter = new OrbitalCenter();
        Assert.assertEquals(defaultCenter.getPosition(), galaxy.getCenter().getPosition());
    }
    private Galaxy createGalaxyWithThreeComponent(){
        final Galaxy.GalaxyBuilder builder = new Galaxy.GalaxyBuilder();
        builder.withCenter(new OrbitalCenter());
        final OrbitalComponent component1 = new Planet(planet1Position, speed1);
        final ArrayList<OrbitalComponent> components = new ArrayList<>();
        components.add(component1);
        final OrbitalComponent component2 = new Planet(planet2Position, speed2);
        components.add(component2);
        final OrbitalComponent component3 = new Planet(planet3Position, speed3);
        components.add(component3);
        builder.withComponents(components);
        return builder.create();
    }

    @Test public void galaxyCreatedWithComponentsTest(){
        final Galaxy galaxy = createGalaxyWithThreeComponent();
        final OrbitalComponent orbitalComponent = galaxy.getComponents().get(0);
        Assert.assertEquals(orbitalComponent.getPosition(), planet1Position);
        Assert.assertEquals(orbitalComponent.getSpeed(), speed1);
        final OrbitalComponent orbitalComponent2 = galaxy.getComponents().get(1);
        Assert.assertEquals(orbitalComponent2.getPosition(), planet2Position);
        Assert.assertEquals(orbitalComponent2.getSpeed(), speed2);
        final OrbitalComponent orbitalComponent3 = galaxy.getComponents().get(2);
        Assert.assertEquals(orbitalComponent3.getPosition(), planet3Position);
        Assert.assertEquals(orbitalComponent3.getSpeed(), speed3);
    }

    @Test public void galaxyFinishDayTest(){
        final Galaxy galaxy = createGalaxyWithThreeComponent();
        galaxy.newDay();
        final OrbitalComponent orbitalComponent = galaxy.getComponents().get(0);
        final GalaxyPosition newPosition = new GalaxyPosition(500.0, 0.0);
        Assert.assertEquals(orbitalComponent.getPosition(), newPosition);

        final OrbitalComponent orbitalComponent2 = galaxy.getComponents().get(1);
        final GalaxyPosition newPosition2 = new GalaxyPosition(0.0, -500.0);
        Assert.assertEquals(orbitalComponent2.getPosition(), newPosition2);

        final OrbitalComponent orbitalComponent3 = galaxy.getComponents().get(2);
        final GalaxyPosition newPosition3 = new GalaxyPosition(-500.0, 0.0);
        Assert.assertEquals(orbitalComponent3.getPosition(), newPosition3);
    }

}