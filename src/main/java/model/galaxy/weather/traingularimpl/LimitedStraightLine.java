package model.galaxy.weather.traingularimpl;

import model.galaxy.movement.GalaxyPosition;

public class LimitedStraightLine extends StraightLine{
    final GalaxyPosition pos1;
    final GalaxyPosition pos2;

    protected LimitedStraightLine(StraightLine line, GalaxyPosition _pos1, GalaxyPosition _pos2){
        super(line.slope, line.intercept);
        pos1 = _pos1;
        pos2 = _pos2;
    }

    /**
     * @return sqrt( (x2-x1)^2 + (y2-y1)^2 )
     */
    Double getDistance(){
        final Double xResult = Math.pow(pos2.x - pos1.x, 2);
        final Double yResult = Math.pow(pos2.y - pos1.y, 2);
        return Math.sqrt(xResult + yResult);
    }
}
