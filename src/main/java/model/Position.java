package model;


import javax.validation.constraints.NotNull;

public class Position {
    final Double x;
    final Double y;
    final Integer degree;

    public Position(@NotNull Double _x, @NotNull Double _y, @NotNull Integer _degree){
        x = _x;
        y = _y;
        degree = _degree;
    }

    @Override
    public boolean equals(Object other) {
        ()
        return super.equals(obj);
    }
}
