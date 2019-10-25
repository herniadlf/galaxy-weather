package model.galaxy;

import model.galaxy.movement.OrbitalMovable;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Galaxy {
    private final List<OrbitalComponent> components = new ArrayList<>();
    private OrbitalCenter center;


    private Galaxy(){
        center = new OrbitalCenter();
    }

    public OrbitalCenter getCenter() {
        return center;
    }
    public List<OrbitalComponent> getComponents() {
        return components;
    }

    public void finishDay() {
        components.forEach(OrbitalMovable::move);
    }

    public static class GalaxyBuilder {
        private final List<OrbitalComponent> componentsBuilder = new ArrayList<>();
        private OrbitalCenter centerBuilder = null;

        public GalaxyBuilder(){}

        public GalaxyBuilder withCenter(@NotNull OrbitalCenter _center){
            centerBuilder = _center;
            return this;
        }
        public GalaxyBuilder withComponents(@NotNull List<OrbitalComponent> _components){
            componentsBuilder.addAll(_components);
            return this;
        }

        public Galaxy create(){
            assert centerBuilder != null;
            final Galaxy galaxy = new Galaxy();
            galaxy.components.addAll(componentsBuilder);
            galaxy.center = centerBuilder;
            return galaxy;
        }
    }
}
