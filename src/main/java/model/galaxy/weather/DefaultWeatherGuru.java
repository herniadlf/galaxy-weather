package model.galaxy.weather;

import model.galaxy.OrbitalCenter;
import model.galaxy.OrbitalComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * The DefaultWeatherGuru class is a implementation that solves the given exercise
 * It also serves as example for further implementations
 */
public class DefaultWeatherGuru implements WeatherGuru{
    final OrbitalCenter center;
    final List<OrbitalComponent> components;

    public DefaultWeatherGuru(OrbitalCenter _center, List<OrbitalComponent> _components){
        center = _center;
        components = new ArrayList<>(_components);
    }

    public GalaxyWeather calculateWeather(){
        throw new RuntimeException("Not yet");
    }
    public Boolean allAligned(){
        return false;
    }
    public Boolean componentsAligned(){
        return false;
    }
    public Boolean centerIsSurrended(){
        return false;
    }
}
