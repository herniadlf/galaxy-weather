package model.galaxy.weather.traingularimpl;

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
        return slope.equals(Double.MAX_VALUE) ? 0.0 : pos.y - (slope*pos.x);
    }

    protected static StraightLine buildLine(GalaxyPosition pos1, GalaxyPosition pos2){
        final Double slope = getSlope(pos1, pos2);
        final Double intercept = getIntercept(slope, pos1);
        if (slope.equals(Double.MAX_VALUE) && intercept.equals(0.0)) // in that case, is a parallel to Y line
            return new ParallelToYStraightLine(new StraightLine(slope,intercept), pos1.x);
        return new StraightLine(slope, intercept);
    }

    /**
     * @return we try to solve the ecuation to check if the line contains the point
     */
    protected Boolean contains(@NotNull GalaxyPosition point) {
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
