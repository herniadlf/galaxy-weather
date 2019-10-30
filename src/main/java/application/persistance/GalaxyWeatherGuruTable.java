package application.persistance;

import javax.persistence.*;

@Entity
@Table(name = "GALAXY_WEATHER_GURU")
public class GalaxyWeatherGuruTable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="WEATHER_GURU_CODE", nullable = false)
    private String weatherGuruCode; // which implementation of WeatherGuru is using

    public String getWeatherGuruCode() {
        return weatherGuruCode;
    }

    public void setWeatherGuruCode(String weatherGuruCode) {
        this.weatherGuruCode = weatherGuruCode;
    }
}
