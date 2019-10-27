package model.galaxy.weather.traingularimpl;

import model.galaxy.movement.GalaxyPosition;
import org.junit.Assert;
import org.junit.Test;

public class StraightLineTest {

    @Test public void parallelToXLineTest(){
        final GalaxyPosition pos1 = new GalaxyPosition(0.0, 100.0);
        final GalaxyPosition pos2 = new GalaxyPosition(200.0, 100.0);

        final StraightLine line = StraightLine.buildLine(pos1, pos2);
        Assert.assertEquals(true, line.contains(new GalaxyPosition(100.0, 100.0)));
        Assert.assertEquals(false, line.contains(new GalaxyPosition(100.0, 0.0)));
        Assert.assertEquals(false, line.contains(new GalaxyPosition(200.0, -100.0)));
        Assert.assertEquals(false, line.contains(new GalaxyPosition(0.0, 0.0)));
        Assert.assertEquals(true, line.contains(new GalaxyPosition(Double.MAX_VALUE, 100.0)));
        Assert.assertEquals(true, line.contains(new GalaxyPosition(-Double.MAX_VALUE, 100.0)));
        Assert.assertEquals(true, line.contains(new GalaxyPosition(Double.MIN_VALUE, 100.0)));
        Assert.assertEquals(true, line.contains(new GalaxyPosition(-Double.MIN_VALUE, 100.0)));
    }

    @Test public void parallelToYLineTest(){
        final GalaxyPosition pos1 = new GalaxyPosition(100.0, 0.0);
        final GalaxyPosition pos2 = new GalaxyPosition(100.0, 200.0);

        final StraightLine line = StraightLine.buildLine(pos1, pos2);
        Assert.assertEquals(true, line.contains(new GalaxyPosition(100.0, 100.0)));
        Assert.assertEquals(false, line.contains(new GalaxyPosition(0.0, 100.0)));
        Assert.assertEquals(false, line.contains(new GalaxyPosition(-100.0, 200.0)));
        Assert.assertEquals(false, line.contains(new GalaxyPosition(0.0, 0.0)));
        Assert.assertEquals(true, line.contains(new GalaxyPosition(100.0,Double.MAX_VALUE)));
        Assert.assertEquals(true, line.contains(new GalaxyPosition(100.0,-Double.MAX_VALUE)));
        Assert.assertEquals(true, line.contains(new GalaxyPosition(100.0,Double.MIN_VALUE)));
        Assert.assertEquals(true, line.contains(new GalaxyPosition(100.0,-Double.MIN_VALUE)));
    }

}