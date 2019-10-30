package model.galaxy.weather;

import model.galaxy.movement.GalaxyPosition;

import java.util.List;

public interface WeatherGuru<T extends GalaxyContainer> {

    String getCode();

    GalaxyWeather calculateWeather();

    Boolean centerAndComponentsAlligned(GalaxyPosition center, List<GalaxyPosition> componentsPosition);

    Boolean onlyComponentsAlligned(List<GalaxyPosition> components);

    default Boolean centerIsSurrounded(GalaxyPosition center, List<GalaxyPosition> components){
        if (onlyComponentsAlligned(components)) return false;
        final T galaxyContainer = buildGalaxyContainer(components);
        return galaxyContainer.contains(center);
    }

    T buildGalaxyContainer(List <GalaxyPosition> componentsPosition);
}
