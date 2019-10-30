package application.controller;

import application.GalaxyProperties;
import application.persistance.service.GalaxyService;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/galaxy")
public class GalaxyController {

    private final GalaxyService service;

    private GalaxyProperties galaxyProperties = null;

    @Autowired
    public void setGalaxyProps(GalaxyProperties galaxyProperties) {
        this.galaxyProperties = galaxyProperties;
    }
    @Autowired
    public GalaxyController(GalaxyService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String create(){
        service.create(buildGalaxyHACK());
        return "ok";
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
            final String name = planetProp.getName();
            final OrbitalSpeed planetSpeed = new OrbitalSpeed(orientation, speedRate);
            final Planet planet = new Planet(name, initPos, planetSpeed);
            planets.add(planet);
        });
        final Galaxy galaxy = builder.withComponents(planets).create();

        Date date = new Date();
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, galaxyProperties.getYears());
        final Date endDate = c.getTime();

        c.setTime(date);
        while (date.before(endDate)){
            galaxy.newDay();
            c.add(Calendar.DAY_OF_YEAR, 1);
            date = c.getTime();
        }
        return galaxy;
    }

}