package persistance;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "GALAXY_DAY_COMPONENT_POSITON")
public class GalaxyDayComponentPositionTable implements Serializable {
    private static final long serialVersionUID = -4113844966151850256L;
    @EmbeddedId
    private GalaxyDayComponentPositionPK galaxyDayComponentPositionPK;

    @OneToOne
    @JoinColumn(name="GALAXY_COMPONENT_ID", referencedColumnName="ID")
    private GalaxyComponentTable galaxyComponentId;

    @Column(name="POSITION_X", nullable = false)
    private Double positionX;
    @Column(name="POSITION_Y", nullable = false)
    private Double positionY;
}
