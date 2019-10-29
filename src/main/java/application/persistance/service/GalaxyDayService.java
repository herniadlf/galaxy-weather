package application.persistance.service;

import application.persistance.GalaxyDayTable;
import application.persistance.repository.GalaxyDayRepository;
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
}
