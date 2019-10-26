package model.galaxy;

import model.galaxy.movement.OrbitalMovable;
import model.galaxy.weather.defaultimpl.DefaultWeatherGuru;
import model.galaxy.weather.WeatherGuru;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Galaxy Class contains a center and orbital components around it
 * Also, it needs to declare a implementation of 'WeatherGuru'
 * the interface that read the components position and provides info about the galaxy weather.
 */
public class Galaxy {
    private List<OrbitalComponent> components;
    private OrbitalCenter center;
    private WeatherGuru weatherGuru;

    private Galaxy(){}

    public OrbitalCenter getCenter() {
        return center;
    }
    public List<OrbitalComponent> getComponents() {
        return components;
    }

    public void newDay() {
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
            galaxy.components = new ArrayList<>(componentsBuilder);
            galaxy.center = centerBuilder;
            galaxy.weatherGuru = new DefaultWeatherGuru(galaxy.center, galaxy.components);
            return galaxy;
        }
    }
}
