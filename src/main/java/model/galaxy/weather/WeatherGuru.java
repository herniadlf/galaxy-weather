package model.galaxy.weather;

import model.galaxy.movement.GalaxyPosition;

import java.util.List;

public interface WeatherGuru<T extends GalaxyContainer> {

    GalaxyWeather calculateWeather();

    Boolean centerAndComponentsAlligned(GalaxyPosition center, List<GalaxyPosition> componentsPosition);

    Boolean onlyComponentsAlligned(List<GalaxyPosition> components);

    default Boolean centerIsSurrounded(GalaxyPosition center, List<GalaxyPosition> components){
        if (onlyComponentsAlligned(components)) return false;
        final T figure = buildGalaxyContainer(components);
        return figure.contains(center);
    }

    T buildGalaxyContainer(List <GalaxyPosition> componentsPosition);
}
