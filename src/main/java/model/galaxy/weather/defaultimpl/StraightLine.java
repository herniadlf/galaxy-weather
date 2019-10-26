package model.galaxy.weather.defaultimpl;

import model.galaxy.movement.GalaxyPosition;

import javax.validation.constraints.NotNull;

public class StraightLine {
    final Double slope;
    final Double intercept;

    protected StraightLine(Double _slope, Double _intercept){
        slope = _slope;
        intercept = _intercept;
    }

    /**
     * @return m as slope
     * y1-y2 = m(x1 - x2) => m = (y1-y2) / (x1-x2)
     * In order to avoid infinite, if 'x1-x2' is zero, we wire the MAX VALUE for slope
     */
    protected static Double getSlope(GalaxyPosition pos1, GalaxyPosition pos2){
        final Double yResult = pos1.y - pos2.y;
        final Double xResult = pos1.x - pos2.x;
        return xResult.equals(0.0) ? Double.MAX_VALUE : yResult/xResult;
    }

    /**
     * @return b as intercept
     * y = m*x + b => b = y - m*x
     */
    protected static Double getIntercept(Double slope, GalaxyPosition pos){
        return pos.y - (slope*pos.x);
    }

    protected static StraightLine buildLine(GalaxyPosition pos1, GalaxyPosition pos2){
        final Double slope = getSlope(pos1, pos2);
        final Double intercept = getIntercept(slope, pos1);
        return new StraightLine(slope, intercept);
    }

    /**
     * @return we check if the previously line contains 'component' position(a point)
     * If the slope is INFINITE we only check for X axis matching
     */
    protected Boolean contains(@NotNull GalaxyPosition point) {
        if (slope.equals(Double.MAX_VALUE)){
            return point.x.equals(0.0);
        }
        final Double y = point.y;
        final double lineResult = (slope * point.x) + intercept;
        return y.equals(lineResult);
    }

    protected Boolean contains(GalaxyPosition... points) {
        boolean containsAll = true;
        for (final GalaxyPosition point : points) {
            if (!contains(point)){
                containsAll = false;
                break;
            }
        }
        return containsAll;
    }
}
