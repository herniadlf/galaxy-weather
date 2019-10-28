package model.galaxy.weather.traingularimpl;

import model.galaxy.movement.GalaxyPosition;

import javax.validation.constraints.NotNull;

public class ParallelToYStraightLine extends StraightLine{
    private final Double xPosition;

    protected ParallelToYStraightLine(StraightLine line, Double _xPosition){
        super(line.slope, line.intercept);
        xPosition = _xPosition;
    }

    @Override public boolean contains(@NotNull GalaxyPosition point) {
        return xPosition.equals(point.x);
    }
}
