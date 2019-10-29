package application.persistance.service;

import application.persistance.GalaxyComponentTable;
import application.persistance.repository.GalaxyComponentRepository;
import model.galaxy.GalaxyComponent;
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

    @Transactional
    public GalaxyComponentTable create(GalaxyComponent component) {
        return galaxyComponentRepository.save(fromComponent(component));
    }

    public static GalaxyComponentTable fromComponent(GalaxyComponent component){
        final GalaxyComponentTable instance = new GalaxyComponentTable();
        instance.setName(component.getName());
        return instance;
    }
}
