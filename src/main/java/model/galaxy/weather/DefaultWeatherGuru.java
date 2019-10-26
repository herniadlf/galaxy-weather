package model.galaxy.weather;

import model.galaxy.OrbitalCenter;
import model.galaxy.OrbitalComponent;
import model.galaxy.movement.GalaxyPosition;

import javax.validation.constraints.NotNull;
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
        if (_components.size() != 3){
            throw new RuntimeException("DefaultWeatherGuru only supports 3 components");
        }
        components = new ArrayList<>(_components);
    }

    public GalaxyWeather calculateWeather(){
        throw new RuntimeException("Not yet");
    }

    public Boolean allAligned(){
        final GalaxyPosition pos1 = center.getPosition();
        final GalaxyPosition pos2 = components.get(0).getPosition();
        final StraightLine line = GeometryUtils.buildLine(pos1, pos2);
        return GeometryUtils.lineContains(line, components.get(1), components.get(2));
    }

    public Boolean componentsAligned(){
        return false;
    }

    public Boolean centerIsSurrended(){
        return false;
    }

    private static class GeometryUtils{
        /**
         * @return m as slope
         * y1-y2 = m(x1 - x2) => m = (y1-y2) / (x1-x2)
         * In order to avoid infinite, if 'x1-x2' is zero, we wire the MAX VALUE for slope
         */
        private static Double getSlope(GalaxyPosition pos1, GalaxyPosition pos2){
            final Double yResult = pos1.y - pos2.y;
            final Double xResult = pos1.x - pos2.x;
            return xResult.equals(0.0) ? Double.MAX_VALUE : yResult/xResult;
        }

        /**
         * @return b as intercept
         * y = m*x + b => b = y - m*x
         */
        private static Double getIntercept(Double slope, GalaxyPosition pos){
            return pos.y - (slope*pos.x);
        }

        private static StraightLine buildLine(GalaxyPosition pos1, GalaxyPosition pos2){
            final Double slope = getSlope(pos1, pos2);
            final Double intercept = getIntercept(slope, pos1);
            return new StraightLine(slope, intercept);
        }

        /**
         * @return we check if the previously line contains 'component' position(a point)
         * If the slope is INFINITE we only check for X axis matching
         */
        private static Boolean lineContains(StraightLine line, @NotNull OrbitalComponent component) {
            if (line.slope.equals(Double.MAX_VALUE)){
                return component.getPosition().x.equals(0.0);
            }
            final Double y = component.getPosition().y;
            final double lineResult = (line.slope * component.getPosition().x) + line.intercept;
            return y.equals(lineResult);
        }

        private static Boolean lineContains(StraightLine line, OrbitalComponent... components) {
            boolean containsAll = true;
            for (final OrbitalComponent component : components) {
                if (!lineContains(line, component)){
                    containsAll = false;
                    break;
                }
            }
            return containsAll;
        }
    }

    private static class StraightLine{
        final Double slope;
        final Double intercept;

        StraightLine(Double _slope, Double _intercept){
            slope = _slope;
            intercept = _intercept;
        }
    }

}
