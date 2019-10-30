package application.persistance;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class GalaxyDayComponentPositionPK implements Serializable {

    private static final long serialVersionUID = -8939177092675788375L;

    @ManyToOne
    @JoinColumn(name="GALAXY_DAY_NUMBER", referencedColumnName="NUMBER")
    private GalaxyDayTable galaxyDay;

    @ManyToOne
    @JoinColumn(name="GALAXY_COMPONENT_ID", referencedColumnName="ID")
    private GalaxyComponentTable galaxyComponent;

    public GalaxyDayTable getGalaxyDay() {
        return galaxyDay;
    }

    public void setGalaxyDay(GalaxyDayTable galaxyDay) {
        this.galaxyDay = galaxyDay;
    }

    public GalaxyComponentTable getGalaxyComponent() {
        return galaxyComponent;
    }

    public void setGalaxyComponent(GalaxyComponentTable galaxyComponent) {
        this.galaxyComponent = galaxyComponent;
    }
}
