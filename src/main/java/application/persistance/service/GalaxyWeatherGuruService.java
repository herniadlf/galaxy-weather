package application.persistance.service;

import application.persistance.GalaxyWeatherGuruTable;
import application.persistance.repository.GalaxyWeatherGuruRepository;
import model.galaxy.Galaxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class GalaxyWeatherGuruService {

    private final GalaxyWeatherGuruRepository galaxyWeatherGuruRepository;

    @Autowired
    public GalaxyWeatherGuruService(GalaxyWeatherGuruRepository galaxyWeatherGuruRepository) {
        this.galaxyWeatherGuruRepository = galaxyWeatherGuruRepository;
    }

    @Transactional
    public List<GalaxyWeatherGuruTable> findGalaxyWeatherGuru() {
        return galaxyWeatherGuruRepository.findAll();
    }

    @Transactional
    public GalaxyWeatherGuruTable create(@NotNull Galaxy galaxy) {
        return galaxyWeatherGuruRepository.save(fromGalaxy(galaxy));
    }

    public GalaxyWeatherGuruTable fromGalaxy(@NotNull Galaxy galaxy){
        final GalaxyWeatherGuruTable instance = new GalaxyWeatherGuruTable();
        instance.setWeatherGuruCode(galaxy.getWeatherGuruCode());
        return galaxyWeatherGuruRepository.save(instance);
    }
}
