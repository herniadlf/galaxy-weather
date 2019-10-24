package model.galaxy.movement;


import javax.validation.constraints.NotNull;
import java.util.Objects;

public class GalaxyPosition {
    final Double x;
    final Double y;

    public GalaxyPosition(@NotNull Double _x, @NotNull Double _y){
        x = _x;
        y = _y;
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
}
