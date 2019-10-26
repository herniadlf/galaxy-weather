package model.galaxy.weather;

import model.galaxy.OrbitalCenter;
import model.galaxy.OrbitalComponent;
import model.galaxy.movement.GalaxyPosition;
import model.galaxy.movement.OrbitalSpeed;
import model.galaxy.planet.Planet;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DefaultWeatherGuruTest {

    private Planet planet1;
    private Planet planet2;
    private Planet planet3;
    private List<OrbitalComponent> components = new ArrayList<OrbitalComponent>();

    public DefaultWeatherGuru createGuru1(){
        final GalaxyPosition planet1Position = new GalaxyPosition(0.0, 500.0);
        final OrbitalSpeed planet1speed = new OrbitalSpeed(OrbitalSpeed.ORIENTATION.CLOCKWISE, 45);
        planet1 = new Planet(planet1Position, planet1speed);
        components.add(planet1);
        final GalaxyPosition planet2Position = new GalaxyPosition(0.0, 300.0);
        final OrbitalSpeed planet2speed = new OrbitalSpeed(OrbitalSpeed.ORIENTATION.CLOCKWISE, 45);
        planet2 = new Planet(planet2Position, planet2speed);
        components.add(planet2);
        final GalaxyPosition planet3Position = new GalaxyPosition(0.0, 200.0);
        final OrbitalSpeed planet3speed = new OrbitalSpeed(OrbitalSpeed.ORIENTATION.CLOCKWISE, 45);
        planet3 = new Planet(planet3Position, planet3speed);
        components.add(planet3);
        return new DefaultWeatherGuru(new OrbitalCenter(), components);
    }

    public DefaultWeatherGuru createGuru2(){
        final GalaxyPosition planet1Position = new GalaxyPosition(500.0, 0.0);
        final OrbitalSpeed planet1speed = new OrbitalSpeed(OrbitalSpeed.ORIENTATION.CLOCKWISE, 45);
        planet1 = new Planet(planet1Position, planet1speed);
        components.add(planet1);
        final GalaxyPosition planet2Position = new GalaxyPosition(300.0, 0.0);
        final OrbitalSpeed planet2speed = new OrbitalSpeed(OrbitalSpeed.ORIENTATION.CLOCKWISE, 45);
        planet2 = new Planet(planet2Position, planet2speed);
        components.add(planet2);
        final GalaxyPosition planet3Position = new GalaxyPosition(200.0, 0.0);
        final OrbitalSpeed planet3speed = new OrbitalSpeed(OrbitalSpeed.ORIENTATION.CLOCKWISE, 45);
        planet3 = new Planet(planet3Position, planet3speed);
        components.add(planet3);
        return new DefaultWeatherGuru(new OrbitalCenter(), components);
    }

    public DefaultWeatherGuru createGuru3(){
        final GalaxyPosition planet1Position = new GalaxyPosition(500.0, 0.0);
        final OrbitalSpeed planet1speed = new OrbitalSpeed(OrbitalSpeed.ORIENTATION.CLOCKWISE, 15);
        planet1 = new Planet(planet1Position, planet1speed);
        components.add(planet1);
        final GalaxyPosition planet2Position = new GalaxyPosition(300.0, 0.0);
        final OrbitalSpeed planet2speed = new OrbitalSpeed(OrbitalSpeed.ORIENTATION.CLOCKWISE, 25);
        planet2 = new Planet(planet2Position, planet2speed);
        components.add(planet2);
        final GalaxyPosition planet3Position = new GalaxyPosition(200.0, 0.0);
        final OrbitalSpeed planet3speed = new OrbitalSpeed(OrbitalSpeed.ORIENTATION.CLOCKWISE, 45);
        planet3 = new Planet(planet3Position, planet3speed);
        components.add(planet3);
        return new DefaultWeatherGuru(new OrbitalCenter(), components);
    }

    @Test public void allignedComponentsInYAxisTest(){
        final DefaultWeatherGuru guru = createGuru1();
        Assert.assertEquals(true, guru.allAligned());
    }

    @Test public void allignedComponentsInYAxisMoveTest(){
        final DefaultWeatherGuru guru = createGuru1();
        components.forEach(OrbitalComponent::move);
        Assert.assertEquals(true, guru.allAligned());
    }

    @Test public void allignedComponentsInXAxisTest(){
        final DefaultWeatherGuru guru = createGuru2();
        Assert.assertEquals(true, guru.allAligned());
    }

    @Test public void allignedComponentsInXAxisMoveTest(){
        final DefaultWeatherGuru guru = createGuru2();
        components.forEach(OrbitalComponent::move);
        Assert.assertEquals(true, guru.allAligned());
    }

    @Test public void allignedComponentsWithDiffrentSpeedTest(){
        final DefaultWeatherGuru guru = createGuru3();
        Assert.assertEquals(true, guru.allAligned());
        components.forEach(OrbitalComponent::move);
        Assert.assertEquals(false, guru.allAligned());
    }
}