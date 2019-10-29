package application.persistance;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Embeddable
public class GalaxyDayPK implements Serializable {

    private static final long serialVersionUID = -847101209045495727L;

    @ManyToOne
    @JoinColumn(name="GALAXY_ID", referencedColumnName="ID")
    private GalaxyTable galaxy;

    @Column(name = "NUMBER", nullable = false)
    private Integer dayNumber;

    public GalaxyTable getGalaxy() {
        return galaxy;
    }

    public void setGalaxy(GalaxyTable galaxy) {
        this.galaxy = galaxy;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }
}
