package persistance;

import model.galaxy.weather.GalaxyWeather;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GALAXY_DAY")
public class GalaxyDayTable {
    @EmbeddedId
    private GalaxyDayPK galaxyDayPk;

    @Column(name = "NUMBER", nullable = false)
    private Integer dayNumber;

    @Column(name = "WEATHER", nullable = false)
    private GalaxyWeather galaxyWeather;

    @OneToMany(mappedBy = "galaxyDayComponentPositionPK.galaxyDay")
    private List<GalaxyDayComponentPositionTable> galaxyDayComponentPositions;

}
