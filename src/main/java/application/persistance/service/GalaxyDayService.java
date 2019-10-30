package application.persistance.service;

import application.persistance.GalaxyComponentTable;
import application.persistance.GalaxyDayComponentPositionPK;
import application.persistance.GalaxyDayComponentPositionTable;
import application.persistance.GalaxyDayTable;
import application.persistance.repository.GalaxyDayRepository;
import model.galaxy.Galaxy;
import model.galaxy.weather.GalaxyWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class GalaxyDayService {

    private final GalaxyDayRepository galaxyDayRepository;
    private final GalaxyDayComponentPositionService galaxyDayComponentPositionService;
    private final GalaxyComponentService galaxyComponentService;

    @Autowired
    public GalaxyDayService(GalaxyDayRepository galaxyDayRepository,
                            GalaxyComponentService galaxyComponentService,
                            GalaxyDayComponentPositionService galaxyDayComponentPositionService) {
        this.galaxyDayRepository = galaxyDayRepository;
        this.galaxyComponentService = galaxyComponentService;
        this.galaxyDayComponentPositionService = galaxyDayComponentPositionService;
    }

    @Transactional
    public List<GalaxyDayTable> list() {
        return galaxyDayRepository.findAll();
    }


    public List<GalaxyDayTable> createFromGalaxy(Galaxy galaxy){
        final HashMap<String, GalaxyComponentTable> persistedComponents = new HashMap<>();
        galaxy.getComponents().forEach(comp -> {
            final GalaxyComponentTable component = galaxyComponentService.findOrCreate(comp);
            persistedComponents.put(component.getName(), component);
        });
        final List<GalaxyDayTable> daysToPersist = new ArrayList<>();
        galaxy.getDays().forEach(day -> {
            final GalaxyDayTable instance = new GalaxyDayTable();
            instance.setDayNumber(day.getDayNumber());
            instance.setGalaxyWeather(day.getWeather().name());
            final GalaxyDayTable createdInstance = galaxyDayRepository.save(instance);
            final List<GalaxyDayComponentPositionTable> componentsPosition = new ArrayList<>();
            day.getComponents().forEach(comp -> {
                final GalaxyDayComponentPositionPK componentPositionPK = new GalaxyDayComponentPositionPK();
                componentPositionPK.setGalaxyDay(createdInstance);
                final GalaxyDayComponentPositionTable componentPosition =
                        galaxyDayComponentPositionService.create(componentPositionPK, comp, persistedComponents);
                componentsPosition.add(componentPosition);
            });
            instance.setGalaxyDayComponentPositions(componentsPosition);
            daysToPersist.add(createdInstance);
        });
        return daysToPersist;
    }

    public List<GalaxyDayTable> create(Galaxy galaxy) {
        return createFromGalaxy(galaxy);
    }

    public GalaxyDayTable getDay(@NotNull Long day) {
        final Optional<GalaxyDayTable> dayByPk = galaxyDayRepository.findById(day);
        if (!dayByPk.isPresent()) throw new RuntimeException("Unexistent day");
        return dayByPk.get();
    }

    /**
     * How many days got a weather like 'weather' param
     */
    public Integer getWeatherQuantities(GalaxyWeather weather) {
        return galaxyDayRepository.findAllByGalaxyWeather(weather.name()).size();
    }
}
