package model.galaxy.weather.defaultimpl;

import model.galaxy.movement.GalaxyPosition;

import javax.validation.constraints.NotNull;

public class ParallelToYStraightLine extends StraightLine{
    private final Double xPosition;

    protected ParallelToYStraightLine(StraightLine line, Double _xPosition){
        super(line.slope, line.intercept);
        xPosition = _xPosition;
    }

    @Override
    protected Boolean contains(@NotNull GalaxyPosition point) {
        return xPosition.equals(point.x);
    }
}
