package application.persistance.service;

import application.persistance.GalaxyComponentTable;
import application.persistance.repository.GalaxyComponentRepository;
import model.galaxy.GalaxyComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GalaxyComponentService {

    private final GalaxyComponentRepository galaxyComponentRepository;

    @Autowired
    public GalaxyComponentService(GalaxyComponentRepository galaxyComponentRepository) {
        this.galaxyComponentRepository = galaxyComponentRepository;
    }

    @Transactional
    public GalaxyComponentTable findOrCreate(GalaxyComponent component) {
        final GalaxyComponentTable byName = galaxyComponentRepository.findByName(component.getName());
        if (byName == null)
            return galaxyComponentRepository.save(fromComponent(component));
        return byName;
    }

    public static GalaxyComponentTable fromComponent(GalaxyComponent component){
        final GalaxyComponentTable instance = new GalaxyComponentTable();
        instance.setName(component.getName());
        return instance;
    }
}
