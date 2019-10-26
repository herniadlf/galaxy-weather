package model.galaxy.weather.defaultimpl;

import model.galaxy.Orientation;
import model.galaxy.movement.GalaxyPosition;

import java.util.ArrayList;
import java.util.List;

public class Triangle {
    final List<LimitedStraightLine> lines = new ArrayList<>();
    final List<GalaxyPosition> points = new ArrayList<>();

    /**
     * @param line1 joins point A to point B
     * @param line2 joins point B to point C
     * @param line3 joins point C to point A
     */
    private Triangle(LimitedStraightLine line1, LimitedStraightLine line2, LimitedStraightLine line3){
        lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        initPoints(line1, line2);
    }

    private void initPoints(LimitedStraightLine line1, LimitedStraightLine line2){
        points.add(line1.pos1);
        points.add(line1.pos2);
        if (points.contains(line2.pos1))
            points.add(line2.pos2);
        else
            points.add(line2.pos1);
    }

    Double getPerimeter(){
        return lines.stream().map(LimitedStraightLine::getDistance)
                .reduce(0.0, (acc, distance) -> acc+distance);
    }

    /**
     * Ecuation: (X1 - X3)*(Y2 - Y3) - (Y1 - Y3)*(X2 - X3)
     * @return  used to know if triangle contains a point
     */
    private Orientation getOrientation(){
        final GalaxyPosition pointA = points.get(0);
        final GalaxyPosition pointB = points.get(1);
        final GalaxyPosition pointC = points.get(2);
        final double firstCalc = (pointA.x - pointC.x) * (pointB.y - pointC.y);
        final double secondCalc = (pointA.y - pointC.y) * (pointB.x - pointC.x);
        assert firstCalc != secondCalc;
        return (firstCalc - secondCalc) > 0 ? Orientation.CLOCKWISE : Orientation.COUNTER_CLOCKWISE;
    }

    /**
     * If 'other point' is inside the traingle, it means that the current triangle orientation must match with
     * (A -> B -> POINT) triangle orientation
     * (B -> C -> POINT) triangle orientation
     * (C -> A -> POINT) triangle orientation
     */
    protected Boolean contains(GalaxyPosition otherPoint){
        if (borderContains(otherPoint)) return true;
        final ArrayList<Triangle> triangles = new ArrayList<>();
        triangles.add(buildTriangleWithPoints(points.get(0), points.get(1), otherPoint));
        triangles.add(buildTriangleWithPoints(points.get(1), points.get(2), otherPoint));
        triangles.add(buildTriangleWithPoints(points.get(2), points.get(0), otherPoint));
        final Orientation triangleOrientation = getOrientation();
        return triangles.stream().allMatch(tr -> triangleOrientation == tr.getOrientation());
    }

    private Boolean borderContains(GalaxyPosition otherPoint) {
        return lines.stream().anyMatch(line -> line.contains(otherPoint));
    }

    protected static Triangle buildTriangleWithPoints(GalaxyPosition pointA, GalaxyPosition pointB, GalaxyPosition pointC){
        final StraightLine aToB = StraightLine.buildLine(pointA, pointB);
        final StraightLine bToC = StraightLine.buildLine(pointB, pointC);
        final StraightLine cToA = StraightLine.buildLine(pointC, pointA);
        return new Triangle(new LimitedStraightLine(aToB, pointA, pointB),
                            new LimitedStraightLine(bToC, pointB, pointC),
                            new LimitedStraightLine(cToA, pointC, pointA));
    }

}
