package application.persistance.service;

import application.persistance.GalaxyComponentTable;
import application.persistance.repository.GalaxyComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GalaxyComponentService {

    private final GalaxyComponentRepository galaxyComponentRepository;

    @Autowired
    public GalaxyComponentService(GalaxyComponentRepository galaxyComponentRepository) {
        this.galaxyComponentRepository = galaxyComponentRepository;
    }

    @Transactional
    public List<GalaxyComponentTable> list() {
        return galaxyComponentRepository.findAll();
    }
}
