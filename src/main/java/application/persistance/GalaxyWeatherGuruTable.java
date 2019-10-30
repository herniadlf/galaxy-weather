package application.persistance;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GALAXY_WEATHER_GURU")
public class GalaxyWeatherGuruTable implements Serializable {
    private static final long serialVersionUID = 1467709095289601519L;
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
