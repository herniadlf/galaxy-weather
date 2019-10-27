package model.galaxy.weather.defaultimpl;

import model.galaxy.GalaxyComponent;
import model.galaxy.OrbitalCenter;
import model.galaxy.OrbitalComponent;
import model.galaxy.movement.GalaxyPosition;
import model.galaxy.weather.GalaxyWeather;
import model.galaxy.weather.WeatherGuru;

import java.util.ArrayList;
import java.util.List;

/**
 * The DefaultWeatherGuru class is a implementation that solves the given exercise
 * It also serves as example for further implementations
 */
public class DefaultWeatherGuru implements WeatherGuru<Triangle> {
    final OrbitalCenter center;
    final List<OrbitalComponent> components;

    public DefaultWeatherGuru(OrbitalCenter _center, List<OrbitalComponent> _components){
        center = _center;
        if (_components.size() != 3){
            throw new RuntimeException("DefaultWeatherGuru only supports 3 components");
        }
        components = new ArrayList<>(_components);
    }

    public GalaxyWeather calculateWeather(){
        if (allAligned()) return GalaxyWeather.DROUGHT;
        if (componentsAlligned()) return GalaxyWeather.OPTIMUM;
        if (centerIsSurrounded()) return GalaxyWeather.RAINY;
        return GalaxyWeather.NORMAL;
    }

    public Boolean allAligned(){
        final GalaxyPosition pos1 = center.getPosition();
        final GalaxyPosition pos2 = components.get(0).getPosition();
        final StraightLine line = StraightLine.buildLine(pos1, pos2);
        return line.contains(components.get(1).getPosition(), components.get(2).getPosition());
    }

    public Boolean componentsAlligned(){
        final GalaxyPosition pos1 = components.get(0).getPosition();
        final GalaxyPosition pos2 = components.get(1).getPosition();
        final StraightLine line = StraightLine.buildLine(pos1, pos2);
        return line.contains(components.get(2).getPosition());
    }

    public Boolean centerIsSurrounded(){
        if (componentsAlligned()) return false;
        final Triangle triangle = buildFigure(components.get(0), components.get(1), components.get(2));
        return triangle.contains(center.getPosition());
    }

    @Override
    public Triangle buildFigure(GalaxyComponent... _components) {
        return Triangle.buildTriangleWithPoints(components.get(0).getPosition(),
                components.get(1).getPosition(),
                components.get(2).getPosition());
    }

}
