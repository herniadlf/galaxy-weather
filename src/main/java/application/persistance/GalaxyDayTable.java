package application.persistance;

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
    private String galaxyWeatherStr;

    @OneToMany(mappedBy = "galaxyDayComponentPositionPK.galaxyDay")
    private List<GalaxyDayComponentPositionTable> galaxyDayComponentPositions;

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public GalaxyWeather getGalaxyWeather() {
        return GalaxyWeather.valueOf(galaxyWeatherStr);
    }

    public String getGalaxyWeatherStr() {
        return galaxyWeatherStr;
    }

    public void setGalaxyWeather(GalaxyWeather galaxyWeather) {
        galaxyWeatherStr = galaxyWeather.name();
    }

    public List<GalaxyDayComponentPositionTable> getGalaxyDayComponentPositions() {
        return galaxyDayComponentPositions;
    }

    public void setGalaxyDayComponentPositions(List<GalaxyDayComponentPositionTable> galaxyDayComponentPositions) {
        this.galaxyDayComponentPositions = galaxyDayComponentPositions;
    }
}
