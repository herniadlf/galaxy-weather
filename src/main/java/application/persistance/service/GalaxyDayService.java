package application.persistance.service;

import application.persistance.GalaxyDayPK;
import application.persistance.GalaxyDayTable;
import application.persistance.repository.GalaxyDayRepository;
import model.galaxy.GalaxyDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GalaxyDayService {

    private final GalaxyDayRepository galaxyDayRepository;

    @Autowired
    public GalaxyDayService(GalaxyDayRepository galaxyDayRepository) {
        this.galaxyDayRepository = galaxyDayRepository;
    }

    @Transactional
    public List<GalaxyDayTable> list() {
        return galaxyDayRepository.findAll();
    }

    @Transactional
    public GalaxyDayTable create(GalaxyDayPK pk, GalaxyDay galaxyDay){
        return galaxyDayRepository.save(fromGalaxyDay(pk, galaxyDay));
    }

    public static GalaxyDayTable fromGalaxyDay(GalaxyDayPK pk, GalaxyDay day){
        final GalaxyDayTable instance = new GalaxyDayTable();
        pk.setDayNumber(day.getDayNumber());
        instance.setGalaxyDayPk(pk);
        instance.setGalaxyWeather(day.getWeather());
//        final List<GalaxyDayComponentPositionTable> componentsPosition = new ArrayList<>();
//        day.getComponents().forEach(comp -> {
//            GalaxyDayComponentPositionService.fromComponent(comp);
//        });
//        instance.setGalaxyDayComponentPositions(new ArrayList<>());
        return instance;
    }
}
