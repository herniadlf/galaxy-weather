package model.galaxy;

import model.galaxy.movement.OrbitalMovable;
import model.galaxy.weather.traingularimpl.TriangleWeatherGuru;
import model.galaxy.weather.WeatherGuru;

import javax.validation.constraints.NotNull;
import java.util.*;

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
            galaxy.weatherGuru = getWeatherGuru(galaxy.center, galaxy.components);
            return galaxy;
        }

        private static WeatherGuru<?> getWeatherGuru(OrbitalCenter _center, List<OrbitalComponent> _components){
            if (_components.size() == 3) return new TriangleWeatherGuru(_center, _components);
            final String error = String.format("No WeatherGuru implementations for %d components", _components.size());
            throw new RuntimeException(error);
        }
    }
}
