package model.galaxy.weather.traingularimpl;

import model.galaxy.GalaxyComponent;
import model.galaxy.OrbitalCenter;
import model.galaxy.OrbitalComponent;
import model.galaxy.movement.GalaxyPosition;
import model.galaxy.weather.GalaxyWeather;
import model.galaxy.weather.WeatherGuru;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The TriangleWeatherGuru class is a implementation that solves the given exercise
 * It also serves as example for further implementations
 */
public class TriangleWeatherGuru implements WeatherGuru<Triangle> {
    public static final String TRIANGLE_IMPL_CODE = "TR_WG";
    final OrbitalCenter center;
    final List<OrbitalComponent> components;

    public TriangleWeatherGuru(OrbitalCenter _center, List<OrbitalComponent> _components){
        center = _center;
        if (_components.size() != 3){
            throw new RuntimeException("TriangleWeatherGuru only supports 3 components");
        }
        components = new ArrayList<>(_components);
    }

    public static List<GalaxyPosition> getComponentsPosition(List<OrbitalComponent> components){
        return components.stream().map(GalaxyComponent::getPosition).collect(Collectors.toList());
    }

    @Override
    public String getCode() {
        return TRIANGLE_IMPL_CODE;
    }

    public GalaxyWeather calculateWeather(){
        final List<GalaxyPosition> componentsPosition = getComponentsPosition(components);
        if (centerAndComponentsAlligned(center.getPosition(), componentsPosition)) return GalaxyWeather.DROUGHT;
        if (onlyComponentsAlligned(componentsPosition)) return GalaxyWeather.OPTIMUM;
        if (centerIsSurrounded(center.getPosition(), componentsPosition)) return GalaxyWeather.RAINY;
        return GalaxyWeather.NORMAL;
    }

    @Override
    public Boolean centerAndComponentsAlligned(GalaxyPosition _center, List<GalaxyPosition> _components){
        final GalaxyPosition pos = _components.get(0);
        final StraightLine line = StraightLine.buildLine(_center, pos);
        return line.contains(_components.get(1), _components.get(2));
    }

    @Override
    public Boolean onlyComponentsAlligned(List<GalaxyPosition> componentPosition){
        final GalaxyPosition pos1 = components.get(0).getPosition();
        final GalaxyPosition pos2 = components.get(1).getPosition();
        final StraightLine line = StraightLine.buildLine(pos1, pos2);
        return line.contains(components.get(2).getPosition());
    }

    @Override
    public Triangle buildGalaxyContainer(List<GalaxyPosition> componentPosition) {
        return Triangle.buildTriangleWithPoints(componentPosition.get(0),
                                                componentPosition.get(1),
                                                componentPosition.get(2));
    }
}
