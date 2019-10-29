package application.persistance.service;

import application.persistance.GalaxyComponentTable;
import application.persistance.GalaxyDayComponentPositionTable;
import application.persistance.repository.GalaxyDayComponentPositionRepository;
import model.galaxy.GalaxyComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

//    public static GalaxyDayComponentPositionTable fromComponent(GalaxyComponent component){
//        final GalaxyDayComponentPositionTable instance = new GalaxyDayComponentPositionTable();
//        GalaxyComponentTable component = GalaxyComponentService.fromComponent(component);
//        instance.setPositionX(component.getPosition().x);
//        instance.setPositionY(component.getPosition().y);
//        instance.
//    }
}
