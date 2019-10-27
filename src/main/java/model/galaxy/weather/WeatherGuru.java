package model.galaxy.weather;

import model.galaxy.GalaxyComponent;

public interface WeatherGuru<T> {
    GalaxyWeather calculateWeather();

    Boolean allAligned();

    Boolean componentsAlligned();

    Boolean centerIsSurrounded();

    T buildFigure(GalaxyComponent... components);
}
