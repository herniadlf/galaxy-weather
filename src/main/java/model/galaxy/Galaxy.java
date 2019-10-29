package model.galaxy;

import model.galaxy.movement.OrbitalMovable;
import model.galaxy.weather.GalaxyWeather;
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
    public static final int FIRST_DAY_NUMBER = 1;
    private List<OrbitalComponent> components = null;
    private List<GalaxyDay> days = null;
    private OrbitalCenter center = null;
    private WeatherGuru<?> weatherGuru = null;

    private Galaxy(){}

    public OrbitalCenter getCenter() {
        return center;
    }
    public List<GalaxyDay> getDays() {
        return days;
    }
    public List<OrbitalComponent> getComponents() {
        return components;
    }

    public String getWeatherGuruCode(){
        return weatherGuru.getCode();
    }

    public void newDay() {
        components.forEach(OrbitalMovable::move);
        final GalaxyWeather newDayWeather = weatherGuru.calculateWeather();
        final Integer lastDayNumber = days.get(days.size() - 1).getDayNumber();
        days.add(new GalaxyDay(lastDayNumber+1, components, newDayWeather));
    }

    public GalaxyWeather getDayWeather(Integer day) {
        GalaxyWeather weather = null;
        for (final GalaxyDay galaxyDay : days) {
            if (galaxyDay.getDayNumber().equals(day)){
                weather = galaxyDay.getWeather();
                break;
            }
        }
        if (weather == null) throw new RuntimeException(String.format("Couldn't find weather for day %d", day));
        return weather;
    }

    /**
     * How many days got a weather like 'weather' param
     */
    public Integer getWeatherQuantities(GalaxyWeather weather) {
        Integer quantity = 0;
        for (final GalaxyDay galaxyDay : days) {
            if (galaxyDay.getWeather() == weather)
                quantity++;
        }
        return quantity;
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
            galaxy.days = getInitialDay(galaxy.weatherGuru, galaxy.components);
            return galaxy;
        }

        private static List<GalaxyDay> getInitialDay(WeatherGuru<?> weatherGuru, List<OrbitalComponent> components) {
            final ArrayList<GalaxyDay> days = new ArrayList<>();
            final GalaxyWeather initialWeather = weatherGuru.calculateWeather();
            days.add(new GalaxyDay(FIRST_DAY_NUMBER, components, initialWeather));
            return days;
        }

        private static WeatherGuru<?> getWeatherGuru(OrbitalCenter _center, List<OrbitalComponent> _components){
            if (_components.size() == 3) return new TriangleWeatherGuru(_center, _components);
            final String error = String.format("No WeatherGuru implementations for %d components", _components.size());
            throw new RuntimeException(error);
        }
    }
}
