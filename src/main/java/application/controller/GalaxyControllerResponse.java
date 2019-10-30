package application.controller;

import application.persistance.GalaxyDayTable;
import model.galaxy.weather.GalaxyWeather;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GalaxyControllerResponse {
    private final String message;
    GalaxyControllerResponse(){
        message = "";
    }
    GalaxyControllerResponse(@NotNull String message){
        this.message = message;
    }
    public String getMessage() {
            return message;
        }

    public static class DayWeatherResponse extends GalaxyControllerResponse{
        private final Long day;
        private final String weather;

        DayWeatherResponse(Long day, String weather, String details){
            this.day = day;
            this.weather = weather;
        }

        public Long getDay() {
            return day;
        }

        public String getWeather() {
            return weather;
        }
    }

    public static class PeriodWeatherResponse extends GalaxyControllerResponse{
        private final String weather;
        private final Integer quantity;

        PeriodWeatherResponse(GalaxyWeather.TYPE weather, Integer quantity){
            this.weather = weather.name();
            this.quantity = quantity;
        }

        public String getWeather() {
            return weather;
        }

        public Integer getQuantity() {
            return quantity;
        }
    }

    public static class PeriodWeatherDetailResponse extends PeriodWeatherResponse{
        private final String detailKey;
        private final String detailDay;

        PeriodWeatherDetailResponse(GalaxyWeather.TYPE weather,
                                    Integer quantity,
                                    String detailKey,
                                    @Nullable GalaxyDayTable detailDay){
            super(weather, quantity);
            this.detailKey = detailKey;
            this.detailDay = detailDay == null ? "" : String.valueOf(detailDay.getDayNumber());
        }

        public String getDetailKey() {
            return detailKey;
        }

        public String getDetailValue() {
            return detailDay;
        }
    }

    public static class WeatherTypesResponse extends GalaxyControllerResponse{
        private final List<GalaxyWeather.TYPE> types = new ArrayList<>();
        WeatherTypesResponse(){
            types.addAll(Arrays.asList(GalaxyWeather.TYPE.values()));
        }
        public List<GalaxyWeather.TYPE> getTypes(){
            return types;
        }
    }

}

