package application.persistance;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GALAXY_DAY")
public class GalaxyDayTable {
    @Id
    @Column(name="NUMBER")
    private Long dayNumber;

    @Column(name = "WEATHER", nullable = false)
    private String galaxyWeather;

    @Column(name = "WEATHER_DETAIL")
    private String galaxyWeatherDetail;

    @OneToMany(mappedBy = "galaxyDayComponentPositionPK.galaxyDay")
    private List<GalaxyDayComponentPositionTable> galaxyDayComponentPositions;

    public String getGalaxyWeather() {
        return galaxyWeather;
    }

    public void setGalaxyWeather(String galaxyWeather) {
        this.galaxyWeather = galaxyWeather;
    }

    public Long getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Long dayNumber) {
        this.dayNumber = dayNumber;
    }

    public List<GalaxyDayComponentPositionTable> getGalaxyDayComponentPositions() {
        return galaxyDayComponentPositions;
    }

    public void setGalaxyDayComponentPositions(List<GalaxyDayComponentPositionTable> galaxyDayComponentPositions) {
        this.galaxyDayComponentPositions = galaxyDayComponentPositions;
    }

    public String getGalaxyWeatherDetail() {
        return galaxyWeatherDetail;
    }

    public void setGalaxyWeatherDetail(String galaxyWeatherDetail) {
        this.galaxyWeatherDetail = galaxyWeatherDetail;
    }
}
