package application.persistance;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GALAXY_COMPONENT")
public class GalaxyComponentTable {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "galaxyDayComponentPositionPK.galaxyComponent")
    private List<GalaxyDayComponentPositionTable> galaxyDayComponentPositions;
}
