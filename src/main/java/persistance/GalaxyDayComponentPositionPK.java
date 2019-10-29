package persistance;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class GalaxyDayComponentPositionPK implements Serializable {

    private static final long serialVersionUID = -8939177092675788375L;
    @ManyToOne
    @JoinColumn(name="GALAXY_ID", referencedColumnName="ID")
    private GalaxyTable galaxy;

    @ManyToOne
    @JoinColumn(name="GALAXY_DAY_NUMBER", referencedColumnName="NUMBER")
    private GalaxyDayTable galaxyDay;
}
