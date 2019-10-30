package model.galaxy;

import model.galaxy.weather.GalaxyWeather;

import java.util.ArrayList;
import java.util.List;

public class GalaxyDay {
    private final Long dayNumber;
    private final List<OrbitalComponent> components;
    private final GalaxyWeather weather;

    public GalaxyDay(Long _dayNumber, List<OrbitalComponent> _components, GalaxyWeather _weather){
        dayNumber = _dayNumber;
        components = new ArrayList<>();
        _components.forEach(comp -> {
            final OrbitalComponent newOc = comp.copy();
            components.add(newOc);
        });
        weather = _weather;
    }

    public Long getDayNumber(){
        return dayNumber;
    }

    public List<OrbitalComponent> getComponents(){
        return components;
    }

    public GalaxyWeather getWeather(){
        return weather;
    }
}
