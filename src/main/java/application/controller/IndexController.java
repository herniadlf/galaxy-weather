package application.controller;

import model.galaxy.Galaxy;
import model.galaxy.OrbitalCenter;
import model.galaxy.OrbitalComponent;
import model.galaxy.Orientation;
import model.galaxy.movement.GalaxyPosition;
import model.galaxy.movement.OrbitalSpeed;
import model.galaxy.planet.Planet;
import model.galaxy.weather.GalaxyWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class IndexController {

    private GalaxyProperties galaxyProperties = null;

    @Autowired
    public void setGalaxyProps(GalaxyProperties galaxyProperties) {
        this.galaxyProperties = galaxyProperties;
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Galaxy Weather!";
    }

    @RequestMapping("/clima")
    String getWeatherByDay(@RequestParam("dia") Integer day) {
        final Galaxy galaxy = buildGalaxyHACK();
        final GalaxyWeather dayWeather = galaxy.getDayWeather(day);
        return String.format("{ \"dia\": %d, \"clima\": \"%s\" }", day, dayWeather);
    }

    @RequestMapping("/tipos_de_clima")
    String getWeatherTypes() {
        final StringBuilder responseBuilder = new StringBuilder("{ \"tipos_de_clima\" :");
        final StringBuilder arrayBuilder = new StringBuilder("[ ");
        for (final GalaxyWeather type : GalaxyWeather.values()) {
            arrayBuilder.append(String.format(" \"%s\",", type));
        }
        String array = arrayBuilder.toString();
        array = array.substring(0, array.length()-1);
        responseBuilder.append(array);
        responseBuilder.append("] }");
        return responseBuilder.toString();
    }

    @RequestMapping("/cantidad_de_periodos")
    String getWeatherQuantities(@RequestParam("tipo") GalaxyWeather weather) {
        final Galaxy galaxy = buildGalaxyHACK();
        final Integer responseQty = galaxy.getWeatherQuantities(weather);
        return String.format("{ \"tipo\" : \"%s\", \"cantidad\": %d }", weather, responseQty);
    }

    private Galaxy buildGalaxyHACK() {
        final Galaxy.GalaxyBuilder builder = new Galaxy.GalaxyBuilder();
        builder.withCenter(new OrbitalCenter());
        final List<OrbitalComponent> planets = new ArrayList<>();
        galaxyProperties.getPlanets().forEach(planetProp -> {
            final double initPositionX = planetProp.getInitPositionX();
            final double initPositionY = planetProp.getInitPositionY();
            final GalaxyPosition initPos = new GalaxyPosition(initPositionX, initPositionY);
            final Orientation orientation = planetProp.getClockWise() ? Orientation.CLOCKWISE :
                                                                        Orientation.COUNTER_CLOCKWISE;
            final int speedRate = planetProp.getSpeed();
            final OrbitalSpeed planetSpeed = new OrbitalSpeed(orientation, speedRate);
            final Planet planet = new Planet(initPos, planetSpeed);
            planets.add(planet);
        });
        final Galaxy galaxy = builder.withComponents(planets).create();
        for (int i=1; i < 3650; i++){
            galaxy.newDay();
        }
        return galaxy;
    }

}