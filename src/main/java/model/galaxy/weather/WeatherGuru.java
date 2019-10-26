package model.galaxy.weather;

public interface WeatherGuru {
    GalaxyWeather calculateWeather();
    Boolean allAligned();
    Boolean componentsAlligned();
    Boolean centerIsSurrounded();
}
