package model;

import java.util.ArrayList;
import java.util.List;

public class Galaxy {
    private List<OrbitalComponent> components = new ArrayList<>();
    private OrbitalCenter center;

    Galaxy(){
        center = new OrbitalCenter();
    }

    public OrbitalCenter getCenter() {
        return center;
    }
}
