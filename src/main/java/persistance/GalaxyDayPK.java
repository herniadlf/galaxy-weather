package persistance;


import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Embeddable
public class GalaxyDayPK implements Serializable {

    private static final long serialVersionUID = -847101209045495727L;

    @ManyToOne
    @JoinColumn(name="GALAXY_ID", referencedColumnName="ID")
    private GalaxyTable galaxy;

}
