package application.controller;

import application.GalaxyProperties;
import application.persistance.GalaxyDayTable;
import application.persistance.GalaxyWeatherGuruTable;
import application.persistance.service.GalaxyComponentService;
import application.persistance.service.GalaxyDayService;
import application.persistance.service.GalaxyWeatherGuruService;
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

    private final GalaxyWeatherGuruService galaxyWeatherGuruService;
    private final GalaxyDayService galaxyDayService;
    private final GalaxyComponentService componentService;

    private GalaxyProperties galaxyProperties = null;

    @Autowired
    public void setGalaxyProps(GalaxyProperties galaxyProperties) {
        this.galaxyProperties = galaxyProperties;
    }
    @Autowired
    public GalaxyController(GalaxyWeatherGuruService galaxyWeatherGuruService,
                            GalaxyDayService galaxyDayService,
                            GalaxyComponentService componentService) {
        this.galaxyWeatherGuruService = galaxyWeatherGuruService;
        this.galaxyDayService = galaxyDayService;
        this.componentService = componentService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String create(){
        final Galaxy galaxy = configureGalaxy();
        galaxyWeatherGuruService.create(galaxy);
        galaxyDayService.create(galaxy);
        return "ok";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/")
    public String delete(){
        // We look for galaxy configuration first
        final List<GalaxyWeatherGuruTable> galaxyWeatherGuru = galaxyWeatherGuruService.findGalaxyWeatherGuru();
        if (galaxyWeatherGuru.isEmpty())
            return "Nothing to delete";
        galaxyDayService.delete();
        galaxyWeatherGuruService.delete();
        return "ok";
    }

    @RequestMapping("/clima")
    String getWeatherByDay(@RequestParam("dia") Long day) {
        // We look for galaxy configuration first or we fail
        final List<GalaxyWeatherGuruTable> galaxyWeatherGuru = galaxyWeatherGuruService.findGalaxyWeatherGuru();
        if (galaxyWeatherGuru.isEmpty())
            return "Must call POST galaxyWeatherGuruService \"galaxy/\" first";
        final GalaxyDayTable galaxyDay = galaxyDayService.getDay(day);
        final String dayWeather = galaxyDay.getGalaxyWeather();
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
        // We look for galaxy configuration first or we fail
        final List<GalaxyWeatherGuruTable> galaxyWeatherGuru = galaxyWeatherGuruService.findGalaxyWeatherGuru();
        if (galaxyWeatherGuru.isEmpty())
            return "Must call POST galaxyWeatherGuruService \"galaxy/\" first";
        final Integer responseQty = galaxyDayService.getWeatherQuantities(weather);
        return String.format("{ \"tipo\" : \"%s\", \"cantidad\": %d }", weather, responseQty);
    }

    private Galaxy configureGalaxy() {
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
            componentService.findOrCreate(planet);
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