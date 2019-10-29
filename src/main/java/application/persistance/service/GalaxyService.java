package application.persistance.service;

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

    @Autowired
    public GalaxyService(GalaxyRepository galaxyRepository) {
        this.galaxyRepository = galaxyRepository;
    }

    @Transactional
    public List<GalaxyTable> list() {
        return galaxyRepository.findAll();
    }

    @Transactional
    public GalaxyTable create(@NotNull Galaxy galaxy) {
        return galaxyRepository.save(fromGalaxy(galaxy));
    }

    public static GalaxyTable fromGalaxy(@NotNull Galaxy galaxy){
        final GalaxyTable instance = new GalaxyTable();
        instance.setWeatherGuruCode(galaxy.getWeatherGuruCode());
        instance.setGalaxyDays(new ArrayList<>());
        instance.setGalaxyDayComponentPositions(new ArrayList<>());
        return instance;
    }
}
