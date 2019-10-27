package model.galaxy.weather.traingularimpl;

import model.galaxy.OrbitalCenter;
import model.galaxy.OrbitalComponent;
import model.galaxy.Orientation;
import model.galaxy.movement.GalaxyPosition;
import model.galaxy.movement.OrbitalSpeed;
import model.galaxy.planet.Planet;
import model.galaxy.weather.GalaxyWeather;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TriangleWeatherGuruTest {

    private Planet planet1 = null;
    private Planet planet2 = null;
    private Planet planet3 = null;
    private final List<OrbitalComponent> components = new ArrayList<OrbitalComponent>();

    public TriangleWeatherGuru createGuru1(){
        final GalaxyPosition planet1Position = new GalaxyPosition(0.0, 500.0);
        final OrbitalSpeed planet1speed = new OrbitalSpeed(Orientation.CLOCKWISE, 45);
        planet1 = new Planet(planet1Position, planet1speed);
        components.add(planet1);
        final GalaxyPosition planet2Position = new GalaxyPosition(0.0, 300.0);
        final OrbitalSpeed planet2speed = new OrbitalSpeed(Orientation.CLOCKWISE, 45);
        planet2 = new Planet(planet2Position, planet2speed);
        components.add(planet2);
        final GalaxyPosition planet3Position = new GalaxyPosition(0.0, 200.0);
        final OrbitalSpeed planet3speed = new OrbitalSpeed(Orientation.CLOCKWISE, 45);
        planet3 = new Planet(planet3Position, planet3speed);
        components.add(planet3);
        return new TriangleWeatherGuru(new OrbitalCenter(), components);
    }

    public TriangleWeatherGuru createGuru2(){
        final GalaxyPosition planet1Position = new GalaxyPosition(500.0, 0.0);
        final OrbitalSpeed planet1speed = new OrbitalSpeed(Orientation.CLOCKWISE, 45);
        planet1 = new Planet(planet1Position, planet1speed);
        components.add(planet1);
        final GalaxyPosition planet2Position = new GalaxyPosition(300.0, 0.0);
        final OrbitalSpeed planet2speed = new OrbitalSpeed(Orientation.CLOCKWISE, 45);
        planet2 = new Planet(planet2Position, planet2speed);
        components.add(planet2);
        final GalaxyPosition planet3Position = new GalaxyPosition(200.0, 0.0);
        final OrbitalSpeed planet3speed = new OrbitalSpeed(Orientation.CLOCKWISE, 45);
        planet3 = new Planet(planet3Position, planet3speed);
        components.add(planet3);
        return new TriangleWeatherGuru(new OrbitalCenter(), components);
    }

    public TriangleWeatherGuru createGuru3(){
        final GalaxyPosition planet1Position = new GalaxyPosition(500.0, 0.0);
        final OrbitalSpeed planet1speed = new OrbitalSpeed(Orientation.CLOCKWISE, 15);
        planet1 = new Planet(planet1Position, planet1speed);
        components.add(planet1);
        final GalaxyPosition planet2Position = new GalaxyPosition(300.0, 0.0);
        final OrbitalSpeed planet2speed = new OrbitalSpeed(Orientation.CLOCKWISE, 25);
        planet2 = new Planet(planet2Position, planet2speed);
        components.add(planet2);
        final GalaxyPosition planet3Position = new GalaxyPosition(200.0, 0.0);
        final OrbitalSpeed planet3speed = new OrbitalSpeed(Orientation.CLOCKWISE, 45);
        planet3 = new Planet(planet3Position, planet3speed);
        components.add(planet3);
        return new TriangleWeatherGuru(new OrbitalCenter(), components);
    }

    public TriangleWeatherGuru createGuru4(){
        final GalaxyPosition planet1Position = new GalaxyPosition(0.0,500.0);
        final OrbitalSpeed planet1speed = new OrbitalSpeed(Orientation.CLOCKWISE, 45);
        planet1 = new Planet(planet1Position, planet1speed);
        components.add(planet1);
        final GalaxyPosition planet2Position = new GalaxyPosition(0.0, 300.0);
        final OrbitalSpeed planet2speed = new OrbitalSpeed(Orientation.COUNTER_CLOCKWISE, 45);
        planet2 = new Planet(planet2Position, planet2speed);
        components.add(planet2);
        final GalaxyPosition planet3Position = new GalaxyPosition(0.0, 200.0);
        final OrbitalSpeed planet3speed = new OrbitalSpeed(Orientation.CLOCKWISE, 180);
        planet3 = new Planet(planet3Position, planet3speed);
        components.add(planet3);
        return new TriangleWeatherGuru(new OrbitalCenter(), components);
    }

    public TriangleWeatherGuru createGuru5(){
        final GalaxyPosition planet1Position = new GalaxyPosition(200.0,500.0);
        final OrbitalSpeed planet1speed = new OrbitalSpeed(Orientation.CLOCKWISE, 45);
        planet1 = new Planet(planet1Position, planet1speed);
        components.add(planet1);
        final GalaxyPosition planet2Position = new GalaxyPosition(200.0, 300.0);
        final OrbitalSpeed planet2speed = new OrbitalSpeed(Orientation.COUNTER_CLOCKWISE, 45);
        planet2 = new Planet(planet2Position, planet2speed);
        components.add(planet2);
        final GalaxyPosition planet3Position = new GalaxyPosition(200.0, 200.0);
        final OrbitalSpeed planet3speed = new OrbitalSpeed(Orientation.CLOCKWISE, 180);
        planet3 = new Planet(planet3Position, planet3speed);
        components.add(planet3);
        return new TriangleWeatherGuru(new OrbitalCenter(), components);
    }

    public TriangleWeatherGuru createGuru6(){
        final GalaxyPosition planet1Position = new GalaxyPosition(0.0,500.0);
        final OrbitalSpeed planet1speed = new OrbitalSpeed(Orientation.CLOCKWISE, 45);
        planet1 = new Planet(planet1Position, planet1speed);
        components.add(planet1);
        final GalaxyPosition planet2Position = new GalaxyPosition(100.0, 300.0);
        final OrbitalSpeed planet2speed = new OrbitalSpeed(Orientation.COUNTER_CLOCKWISE, 45);
        planet2 = new Planet(planet2Position, planet2speed);
        components.add(planet2);
        final GalaxyPosition planet3Position = new GalaxyPosition(100.0, -200.0);
        final OrbitalSpeed planet3speed = new OrbitalSpeed(Orientation.CLOCKWISE, 180);
        planet3 = new Planet(planet3Position, planet3speed);
        components.add(planet3);
        return new TriangleWeatherGuru(new OrbitalCenter(), components);
    }

    @Test public void allAllignedInYAxisTest(){
        final TriangleWeatherGuru guru = createGuru1();
        Assert.assertEquals(true, guru.allAligned());
    }

    @Test public void allignedComponentssInYAxisTest(){
        final TriangleWeatherGuru guru = createGuru1();
        Assert.assertEquals(true, guru.componentsAlligned());
    }

    @Test public void allAllignedInYAxisMoveTest(){
        final TriangleWeatherGuru guru = createGuru1();
        components.forEach(OrbitalComponent::move);
        Assert.assertEquals(true, guru.allAligned());
    }

    @Test public void allignedComponentsInYAxisMoveTest(){
        final TriangleWeatherGuru guru = createGuru1();
        components.forEach(OrbitalComponent::move);
        Assert.assertEquals(true, guru.componentsAlligned());
    }

    @Test public void allAllignedInXAxisTest(){
        final TriangleWeatherGuru guru = createGuru2();
        Assert.assertEquals(true, guru.allAligned());
    }

    @Test public void allignedComponentsInXAxisTest(){
        final TriangleWeatherGuru guru = createGuru2();
        Assert.assertEquals(true, guru.componentsAlligned());
    }

    @Test public void allAllignedInXAxisMoveTest(){
        final TriangleWeatherGuru guru = createGuru2();
        components.forEach(OrbitalComponent::move);
        Assert.assertEquals(true, guru.allAligned());
    }

    @Test public void allignedComponentsInXAxisMoveTest(){
        final TriangleWeatherGuru guru = createGuru2();
        components.forEach(OrbitalComponent::move);
        Assert.assertEquals(true, guru.componentsAlligned());
    }

    @Test public void allAllignedWithDiffrentSpeedTest(){
        final TriangleWeatherGuru guru = createGuru3();
        Assert.assertEquals(true, guru.allAligned());
        components.forEach(OrbitalComponent::move);
        Assert.assertEquals(false, guru.allAligned());
    }

    @Test public void allignedComponentsWithDiffrentSpeedTest(){
        final TriangleWeatherGuru guru = createGuru3();
        Assert.assertEquals(true, guru.componentsAlligned());
        components.forEach(OrbitalComponent::move);
        Assert.assertEquals(false, guru.componentsAlligned());
    }

    @Test public void surroundedCenterTest(){
        final TriangleWeatherGuru guru = createGuru4();
        Assert.assertEquals(false, guru.centerIsSurrounded());
        components.forEach(OrbitalComponent::move);
        Assert.assertEquals(true, guru.centerIsSurrounded());
        components.forEach(OrbitalComponent::move);
        Assert.assertEquals(true, guru.centerIsSurrounded());
    }

    @Test public void allignedWithCenterMeansDroughtWeatherTest(){
        final TriangleWeatherGuru guru = createGuru1();
        Assert.assertEquals(guru.calculateWeather(), GalaxyWeather.DROUGHT);
    }

    @Test public void allignedWithoutCenterMeansOptimumWeatherTest(){
        final TriangleWeatherGuru guru = createGuru5();
        Assert.assertEquals(guru.calculateWeather(), GalaxyWeather.OPTIMUM);
    }

    @Test public void surroundedCenterMeansRainyWeatherTest(){
        final TriangleWeatherGuru guru = createGuru4();
        components.forEach(OrbitalComponent::move);
        Assert.assertEquals(guru.calculateWeather(), GalaxyWeather.RAINY);
    }

    @Test public void notAllignedAndNotSurroundedCenterMeansNormalWeatherTest(){
        final TriangleWeatherGuru guru = createGuru6();
        components.forEach(OrbitalComponent::move);
        Assert.assertEquals(guru.calculateWeather(), GalaxyWeather.NORMAL);
    }

}