package application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldMayBeFinal")
@Component
@PropertySource("classpath:galaxy.properties")
@ConfigurationProperties("galaxy")
public class GalaxyProperties {

    private Integer years = 0;
    private List<PlanetProperties> planets = new ArrayList<>();

    public static class PlanetProperties{
        private double initPositionX = 0.0;
        private double initPositionY = 0.0;
        private int speed = 0;
        private boolean clockwise = true;
        private String name = "";
        public double getInitPositionX(){ return initPositionX; }
        public void setInitPositionX(double _initPositionX){ initPositionX= _initPositionX; }
        public double getInitPositionY(){ return initPositionY; }
        public void setInitPositionY(double _initPositionY){ initPositionY = _initPositionY; }
        public int getSpeed(){ return speed; }
        public void setSpeed(int _speed){ speed = _speed; }
        public boolean getClockWise(){ return clockwise; }
        public void setClockWise(boolean _clockwise){ clockwise = _clockwise; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public List<PlanetProperties> getPlanets(){
        return planets;
    }

    public void setPlanets(List<PlanetProperties> _planets){
        planets = _planets;
    }


    public Integer getYears() { return years; }

    public void setYears(Integer years) { this.years = years; }
}
