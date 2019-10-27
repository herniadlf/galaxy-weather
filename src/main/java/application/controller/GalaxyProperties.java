package application.controller;

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

    private List<PlanetProperties> planets = new ArrayList<>();
    public static class PlanetProperties{
        private int speed = 0;
        private boolean clockwise = true;
        private double distance = 0.0;
        public int getSpeed(){ return speed; }
        public void setSpeed(int _speed){ speed = _speed; }
        public boolean getClockWise(){ return clockwise; }
        public void setClockWise(boolean _clockwise){ clockwise = _clockwise; }
        public double getDistance(){ return distance; }
        public void setDistance(double _distance){ distance = _distance; }
    }

    public List<PlanetProperties> getPlanets(){
        return planets;
    }

    public void setPlanets(List<PlanetProperties> _planets){
        planets = _planets;
    }
}
