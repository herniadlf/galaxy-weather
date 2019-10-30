package application.persistance.service;

import application.GalaxyProperties;
import application.persistance.GalaxyComponentTable;
import application.persistance.GalaxyDayComponentPositionPK;
import application.persistance.GalaxyDayComponentPositionTable;
import application.persistance.GalaxyDayTable;
import application.persistance.repository.GalaxyDayRepository;
import model.galaxy.Galaxy;
import model.galaxy.OrbitalCenter;
import model.galaxy.OrbitalComponent;
import model.galaxy.Orientation;
import model.galaxy.movement.GalaxyPosition;
import model.galaxy.movement.OrbitalSpeed;
import model.galaxy.planet.Planet;
import model.galaxy.weather.GalaxyWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class GalaxyDayService {

    private final GalaxyDayRepository galaxyDayRepository;
    private final GalaxyDayComponentPositionService galaxyDayComponentPositionService;
    private final GalaxyWeatherGuruService galaxyWeatherGuruService;
    private final GalaxyComponentService galaxyComponentService;
    private GalaxyProperties galaxyProperties = null;

    @Autowired
    public void setGalaxyProps(GalaxyProperties galaxyProperties) {
        this.galaxyProperties = galaxyProperties;
    }

    @Autowired
    public GalaxyDayService(GalaxyDayRepository galaxyDayRepository,
                            GalaxyComponentService galaxyComponentService,
                            GalaxyWeatherGuruService galaxyWeatherGuruService,
                            GalaxyDayComponentPositionService galaxyDayComponentPositionService) {
        this.galaxyDayRepository = galaxyDayRepository;
        this.galaxyComponentService = galaxyComponentService;
        this.galaxyWeatherGuruService = galaxyWeatherGuruService;
        this.galaxyDayComponentPositionService = galaxyDayComponentPositionService;
    }

    public void createFromGalaxy(Galaxy galaxy){
        final HashMap<String, GalaxyComponentTable> persistedComponents = new HashMap<>();
        galaxy.getComponents().forEach(comp -> {
            final GalaxyComponentTable component = galaxyComponentService.findOrCreate(comp);
            persistedComponents.put(component.getName(), component);
        });
        final List<GalaxyDayComponentPositionTable> daysComponentPosToPersist = new ArrayList<>();
        galaxy.getDays().forEach(day -> {
            final GalaxyDayTable instance = new GalaxyDayTable();
            instance.setDayNumber(day.getDayNumber());
            instance.setGalaxyWeather(day.getWeather().getType().name());
            instance.setGalaxyWeatherDetail(day.getWeather().getDetailsAsJson());
            final GalaxyDayTable createdInstance = galaxyDayRepository.save(instance);
            day.getComponents().forEach(comp -> {
                final GalaxyDayComponentPositionPK componentPositionPK = new GalaxyDayComponentPositionPK();
                componentPositionPK.setGalaxyDay(createdInstance);
                final GalaxyDayComponentPositionTable componentPosition =
                        galaxyDayComponentPositionService.fromComponentPosition(componentPositionPK, comp, persistedComponents);
                daysComponentPosToPersist.add(componentPosition);
            });
        });
        galaxyDayComponentPositionService.create(daysComponentPosToPersist);
    }

    @Transactional
    public void create() {
        final Galaxy galaxy = configureGalaxy();
        galaxyWeatherGuruService.create(galaxy);
        createFromGalaxy(galaxy);
    }

    @Transactional
    public GalaxyDayTable getDay(@NotNull Long day) {
        final Optional<GalaxyDayTable> dayByPk = galaxyDayRepository.findById(day);
        if (!dayByPk.isPresent()) throw new RuntimeException("Unexistent day");
        return dayByPk.get();
    }

    /**
     * How many days got a weather like 'weather' param
     */
    @Transactional
    public List<GalaxyDayTable> getWeatherQuantities(GalaxyWeather.TYPE weather) {
        return galaxyDayRepository.findAllByGalaxyWeather(weather.name());
    }

    @Transactional
    public void delete() {
        galaxyDayComponentPositionService.delete();
        galaxyComponentService.delete();
        galaxyDayRepository.deleteAll();
    }

    public GalaxyDayTable findGreaterByDetail(List<GalaxyDayTable> days, String detailKey) {
        final HashMap<Double, GalaxyDayTable> map = new HashMap<>();
        days.forEach(day -> {
            final String detail = day.getGalaxyWeatherDetail();
            final BasicJsonParser parser = new BasicJsonParser();
            final Map<String, Object> detailMap = parser.parseMap(detail);
            if (!detailMap.isEmpty()){
                final Double detailValue = (Double) detailMap.get(detailKey);
                map.put(detailValue, day);
            }
        });
        if (map.isEmpty())
            return null;
        final Optional<Double> first = map.keySet().stream().max(Double::compareTo);
        assert first.isPresent();
        return map.get(first.get());
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
            galaxyComponentService.findOrCreate(planet);
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
