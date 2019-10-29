package application.persistance;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GALAXY_DAY_COMPONENT_POSITION")
public class GalaxyDayComponentPositionTable implements Serializable {
    private static final long serialVersionUID = -4113844966151850256L;
    @EmbeddedId
    private GalaxyDayComponentPositionPK galaxyDayComponentPositionPK;

    @Column(name="POSITION_X", nullable = false)
    private Double positionX;
    @Column(name="POSITION_Y", nullable = false)
    private Double positionY;

    public Double getPositionX() {
        return positionX;
    }

    public void setPositionX(Double positionX) {
        this.positionX = positionX;
    }

    public Double getPositionY() {
        return positionY;
    }

    public void setPositionY(Double positionY) {
        this.positionY = positionY;
    }

    public GalaxyDayComponentPositionPK getGalaxyDayComponentPositionPK() {
        return galaxyDayComponentPositionPK;
    }

    public void setGalaxyDayComponentPositionPK(GalaxyDayComponentPositionPK galaxyDayComponentPositionPK) {
        this.galaxyDayComponentPositionPK = galaxyDayComponentPositionPK;
    }
}
