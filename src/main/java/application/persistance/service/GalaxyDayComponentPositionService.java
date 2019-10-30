package application.persistance.service;

import application.persistance.GalaxyComponentTable;
import application.persistance.GalaxyDayComponentPositionPK;
import application.persistance.GalaxyDayComponentPositionTable;
import application.persistance.repository.GalaxyDayComponentPositionRepository;
import model.galaxy.GalaxyComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class GalaxyDayComponentPositionService {

    private final GalaxyDayComponentPositionRepository galaxyDayComponentPositionRepository;

    @Autowired
    public GalaxyDayComponentPositionService(GalaxyDayComponentPositionRepository galaxyDayComponentPositionRepository) {
        this.galaxyDayComponentPositionRepository = galaxyDayComponentPositionRepository;
    }

    @Transactional
    public List<GalaxyDayComponentPositionTable> list() {
        return galaxyDayComponentPositionRepository.findAll();
    }

    @Transactional
    public GalaxyDayComponentPositionTable create(GalaxyDayComponentPositionPK pk,
                                                  GalaxyComponent component,
                                                  HashMap<String, GalaxyComponentTable> persistedComponents) {
        return galaxyDayComponentPositionRepository.save(fromComponentPosition(pk, component, persistedComponents));
    }

    public GalaxyDayComponentPositionTable fromComponentPosition(GalaxyDayComponentPositionPK pk,
                                                                 GalaxyComponent component,
                                                                 HashMap<String, GalaxyComponentTable> persistedComponents){
        final GalaxyDayComponentPositionTable instance = new GalaxyDayComponentPositionTable();
        final GalaxyComponentTable persistedComponent = persistedComponents.get(component.getName());
        pk.setGalaxyComponent(persistedComponent);
        instance.setGalaxyDayComponentPositionPK(pk);
        instance.setPositionX(component.getPosition().x);
        instance.setPositionY(component.getPosition().y);
        return instance;
    }
}
