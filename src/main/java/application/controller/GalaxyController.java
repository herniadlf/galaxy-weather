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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/galaxy")
public class GalaxyController {

    public static final String MUST_POST_FIRST = "Must call POST galaxyWeatherGuruService \"galaxy/\" first";
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
    public ResponseEntity<GalaxyControllerResponse> create(){
        final Galaxy galaxy = configureGalaxy();
        galaxyWeatherGuruService.create(galaxy);
        galaxyDayService.create(galaxy);
        return new ResponseEntity<GalaxyControllerResponse>(OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/")
    public ResponseEntity<GalaxyControllerResponse> delete(){
        // We look for galaxy configuration first
        final List<GalaxyWeatherGuruTable> galaxyWeatherGuru = galaxyWeatherGuruService.findGalaxyWeatherGuru();
        if (galaxyWeatherGuru.isEmpty()){
            final GalaxyControllerResponse response = new GalaxyControllerResponse("Nothing to delete");
            return new ResponseEntity<>(response, OK);
        }
        galaxyDayService.delete();
        galaxyWeatherGuruService.delete();
        return new ResponseEntity<>(OK);
    }

    @RequestMapping("/clima")
    ResponseEntity<GalaxyControllerResponse> getWeatherByDay(@RequestParam("dia") Long day) {
        // We look for galaxy configuration first or we fail
        final List<GalaxyWeatherGuruTable> galaxyWeatherGuru = galaxyWeatherGuruService.findGalaxyWeatherGuru();
        if (galaxyWeatherGuru.isEmpty()) {
            final GalaxyControllerResponse resp = new GalaxyControllerResponse(MUST_POST_FIRST);
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        final GalaxyDayTable galaxyDay = galaxyDayService.getDay(day);
        final String dayWeather = galaxyDay.getGalaxyWeather();
        final String dayWeatherDetail= galaxyDay.getGalaxyWeatherDetail();
        final GalaxyControllerResponse resp = new DayWeatherResponse(galaxyDay.getDayNumber(), dayWeather, dayWeatherDetail);
        return new ResponseEntity<>(resp, OK);
    }

    @RequestMapping("/tipos_de_clima")
    ResponseEntity<GalaxyControllerResponse> getWeatherTypes() {
        return new ResponseEntity<>(new WeatherTypesResponse(), OK);
    }

    @RequestMapping("/cantidad_de_periodos")
    ResponseEntity<GalaxyControllerResponse> getWeatherQuantities(
            @RequestParam("tipo") GalaxyWeather.TYPE weather,
            @RequestParam(value = "detalle", required = false) String detailKey)
    {
        // We look for galaxy configuration first or we fail
        final List<GalaxyWeatherGuruTable> galaxyWeatherGuru = galaxyWeatherGuruService.findGalaxyWeatherGuru();
        if (galaxyWeatherGuru.isEmpty()){
            final GalaxyControllerResponse resp = new GalaxyControllerResponse(MUST_POST_FIRST);
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        final List<GalaxyDayTable> days = galaxyDayService.getWeatherQuantities(weather);
        if (detailKey == null){
            final PeriodWeatherResponse resp = new PeriodWeatherResponse(weather, days.size());
            return new ResponseEntity<>(resp, OK);
        }
        final GalaxyDayTable greaterByDetail = galaxyDayService.findGreaterByDetail(days, detailKey);
        final PeriodWeatherResponse resp = new PeriodWeatherDetailResponse(weather,
                                                                            days.size(),
                                                                            detailKey,
                                                                            greaterByDetail);
        return new ResponseEntity<>(resp, OK);
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

    private class GalaxyControllerResponse {
        private final String message;
        GalaxyControllerResponse(){
            message = "";
        }
        GalaxyControllerResponse(@NotNull String message){
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    private class DayWeatherResponse extends GalaxyControllerResponse{
        private final Long day;
        private final String weather;

        DayWeatherResponse(Long day, String weather, String details){
            this.day = day;
            this.weather = weather;
        }

        public Long getDay() {
            return day;
        }

        public String getWeather() {
            return weather;
        }
    }

    private class PeriodWeatherResponse extends GalaxyControllerResponse{
        private final String weather;
        private final Integer quantity;

        PeriodWeatherResponse(GalaxyWeather.TYPE weather, Integer quantity){
            this.weather = weather.name();
            this.quantity = quantity;
        }

        public String getWeather() {
            return weather;
        }

        public Integer getQuantity() {
            return quantity;
        }
    }

    private class PeriodWeatherDetailResponse extends PeriodWeatherResponse{
        private final String detailKey;
        private final String detailDay;

        PeriodWeatherDetailResponse(GalaxyWeather.TYPE weather,
                              Integer quantity,
                              String detailKey,
                              @Nullable GalaxyDayTable detailDay){
            super(weather, quantity);
            this.detailKey = detailKey;
            this.detailDay = detailDay == null ? "" : String.valueOf(detailDay.getDayNumber());
        }

        public String getDetailKey() {
            return detailKey;
        }

        public String getDetailValue() {
            return detailDay;
        }
    }

    private class WeatherTypesResponse extends GalaxyControllerResponse{
        private final List<GalaxyWeather.TYPE> types = new ArrayList<>();
        WeatherTypesResponse(){
            types.addAll(Arrays.asList(GalaxyWeather.TYPE.values()));
        }
        public List<GalaxyWeather.TYPE> getTypes(){
            return types;
        }
    }
}