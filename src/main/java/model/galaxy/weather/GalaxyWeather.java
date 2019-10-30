package model.galaxy.weather;

import java.util.ArrayList;
import java.util.List;

public class GalaxyWeather {
    public enum TYPE {DROUGHT, RAINY, OPTIMUM, NORMAL}
    private final TYPE type;
    private final List<String> details = new ArrayList<>();

    public GalaxyWeather(TYPE type){
        this.type=type;
    }

    public void addDetail(String detail){
        details.add(detail);
    }

    public String getDetailsAsJson(){
        if (details.isEmpty()) return "{}";
        final StringBuilder stringBuilder = new StringBuilder("{");
        details.forEach(detail -> stringBuilder.append(String.format(" %s,",detail)));
        final String builded = stringBuilder.toString();
        return String.format("%s }",builded.substring(0, builded.length()-1));
    }

    public TYPE getType(){
        return type;
    }
}

