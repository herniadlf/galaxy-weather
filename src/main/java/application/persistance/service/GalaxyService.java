package application.persistance.service;

import application.persistance.GalaxyDayPK;
import application.persistance.GalaxyDayTable;
import application.persistance.GalaxyTable;
import application.persistance.repository.GalaxyRepository;
import model.galaxy.Galaxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class GalaxyService {

    private final GalaxyRepository galaxyRepository;
    private final GalaxyDayService galaxyDayService;

    @Autowired
    public GalaxyService(GalaxyRepository galaxyRepository, GalaxyDayService galaxyDayService) {
        this.galaxyRepository = galaxyRepository;
        this.galaxyDayService = galaxyDayService;
    }


    @Transactional
    public List<GalaxyTable> list() {
        return galaxyRepository.findAll();
    }

    @Transactional
    public GalaxyTable create(@NotNull Galaxy galaxy) {
        return galaxyRepository.save(fromGalaxy(galaxy));
    }

    public GalaxyTable fromGalaxy(@NotNull Galaxy galaxy){
        final GalaxyTable instance = new GalaxyTable();
        instance.setWeatherGuruCode(galaxy.getWeatherGuruCode());
        final GalaxyTable createdInstance = galaxyRepository.save(instance);
        final List<GalaxyDayTable> days = new ArrayList<>();
        galaxy.getDays().forEach(day -> {
            final GalaxyDayPK galaxyDayPK = new GalaxyDayPK();
            galaxyDayPK.setGalaxy(createdInstance);
            final GalaxyDayTable persistedDay = galaxyDayService.create(galaxyDayPK, day);
            days.add(persistedDay);
        });
        createdInstance.setGalaxyDays(days);
//        createdInstance.setGalaxyDayComponentPositions(new ArrayList<>());
        return createdInstance;
    }
}
