package persistance;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "GALAXY")
public class GalaxyTable implements Serializable {
    private static final long serialVersionUID = -8962541304895706686L;
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="WEATHER_GURU_CODE", nullable = false)
    private String weatherGuruCode; // which implementation of WeatherGuru

    @OneToMany(mappedBy = "galaxyDayPk.galaxy")
    private List<GalaxyDayTable> galaxyDays;

    @OneToMany(mappedBy = "galaxyDayComponentPositionPK.galaxy")
    private List<GalaxyDayComponentPositionTable> galaxyDayComponentPositions;
}
