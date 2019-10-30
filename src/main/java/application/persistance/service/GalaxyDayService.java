package application.persistance.service;

import application.persistance.*;
import application.persistance.repository.GalaxyDayRepository;
import model.galaxy.GalaxyDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GalaxyDayService {

    private final GalaxyDayRepository galaxyDayRepository;
    private final GalaxyDayComponentPositionService galaxyDayComponentPositionService;

    @Autowired
    public GalaxyDayService(GalaxyDayRepository galaxyDayRepository,
                            GalaxyDayComponentPositionService galaxyDayComponentPositionService) {
        this.galaxyDayRepository = galaxyDayRepository;
        this.galaxyDayComponentPositionService = galaxyDayComponentPositionService;
    }

    @Transactional
    public List<GalaxyDayTable> list() {
        return galaxyDayRepository.findAll();
    }

    @Transactional
    public GalaxyDayTable create(GalaxyDayPK pk,
                                 GalaxyDay galaxyDay, HashMap<String,
                                 GalaxyComponentTable> persistedComponents){
        return galaxyDayRepository.save(fromGalaxyDay(pk, galaxyDay, persistedComponents));
    }

    public GalaxyDayTable fromGalaxyDay(GalaxyDayPK pk,
                                        GalaxyDay day,
                                        HashMap<String, GalaxyComponentTable> persistedComponents){
        final GalaxyDayTable instance = new GalaxyDayTable();
        pk.setDayNumber(day.getDayNumber());
        instance.setGalaxyDayPk(pk);
        instance.setGalaxyWeather(day.getWeather());
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
        return instance;
    }
}
