package model.galaxy.movement;


import javax.validation.constraints.NotNull;
import java.util.Objects;

public class GalaxyPosition {
    public final Double x;
    public final Double y;

    public GalaxyPosition(@NotNull Double _x, @NotNull Double _y){
        x = correction(_x);
        y = correction(_y);
    }

    @Override
    public boolean equals(Object obj) {
        assert obj instanceof GalaxyPosition;
        final GalaxyPosition other = (GalaxyPosition) obj;
        return x.equals(other.x) && y.equals(other.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    private static final double EPSILON = 1e-7;
    private static Double correction(Double number){
        final double positive = number < 0 ? number*-1 : number;
        return positive < EPSILON ? 0 : number;
    }
}
