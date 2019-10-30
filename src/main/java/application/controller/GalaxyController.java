package application.controller;

import application.persistance.GalaxyDayTable;
import application.persistance.GalaxyWeatherGuruTable;
import application.persistance.service.GalaxyDayService;
import application.persistance.service.GalaxyWeatherGuruService;
import model.galaxy.weather.GalaxyWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/galaxy")
public class GalaxyController {

    public static final String MUST_POST_FIRST = "Must call POST galaxyWeatherGuruService \"galaxy/\" first";
    private final GalaxyWeatherGuruService galaxyWeatherGuruService;
    private final GalaxyDayService galaxyDayService;
    @Autowired
    public GalaxyController(GalaxyWeatherGuruService galaxyWeatherGuruService,
                            GalaxyDayService galaxyDayService) {
        this.galaxyWeatherGuruService = galaxyWeatherGuruService;
        this.galaxyDayService = galaxyDayService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<GalaxyControllerResponse> create(){
        galaxyDayService.create();
        return new ResponseEntity<GalaxyControllerResponse>(OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/")
    public ResponseEntity<GalaxyControllerResponse> delete(){
        // We look for galaxy configuration first
        final List<GalaxyWeatherGuruTable> galaxyWeatherGuru = galaxyWeatherGuruService.findGalaxyWeatherGuru();
        if (galaxyWeatherGuru.isEmpty()){
            final GalaxyControllerResponse response = new GalaxyControllerResponse("Nothing to delete");
            return new ResponseEntity<>(response, OK);
        }
        galaxyDayService.delete();
        galaxyWeatherGuruService.delete();
        return new ResponseEntity<>(OK);
    }

    @RequestMapping("/clima")
    ResponseEntity<GalaxyControllerResponse> getWeatherByDay(@RequestParam("dia") Long day) {
        // We look for galaxy configuration first or we fail
        final List<GalaxyWeatherGuruTable> galaxyWeatherGuru = galaxyWeatherGuruService.findGalaxyWeatherGuru();
        if (galaxyWeatherGuru.isEmpty()) {
            final GalaxyControllerResponse resp = new GalaxyControllerResponse(MUST_POST_FIRST);
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        final GalaxyDayTable galaxyDay = galaxyDayService.getDay(day);
        final String dayWeather = galaxyDay.getGalaxyWeather();
        final String dayWeatherDetail= galaxyDay.getGalaxyWeatherDetail();
        final GalaxyControllerResponse resp = new GalaxyControllerResponse.DayWeatherResponse(galaxyDay.getDayNumber(), dayWeather, dayWeatherDetail);
        return new ResponseEntity<>(resp, OK);
    }

    @RequestMapping("/tipos_de_clima")
    ResponseEntity<GalaxyControllerResponse> getWeatherTypes() {
        return new ResponseEntity<>(new GalaxyControllerResponse.WeatherTypesResponse(), OK);
    }

    @RequestMapping("/cantidad_de_periodos")
    ResponseEntity<GalaxyControllerResponse> getWeatherQuantities(
            @RequestParam("tipo") GalaxyWeather.TYPE weather,
            @RequestParam(value = "detalle", required = false) String detailKey)
    {
        // We look for galaxy configuration first or we fail
        final List<GalaxyWeatherGuruTable> galaxyWeatherGuru = galaxyWeatherGuruService.findGalaxyWeatherGuru();
        if (galaxyWeatherGuru.isEmpty()){
            final GalaxyControllerResponse resp = new GalaxyControllerResponse(MUST_POST_FIRST);
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        final List<GalaxyDayTable> days = galaxyDayService.getWeatherQuantities(weather);
        if (detailKey == null){
            final GalaxyControllerResponse.PeriodWeatherResponse resp = new GalaxyControllerResponse.PeriodWeatherResponse(weather, days.size());
            return new ResponseEntity<>(resp, OK);
        }
        final GalaxyDayTable greaterByDetail = galaxyDayService.findGreaterByDetail(days, detailKey);
        final GalaxyControllerResponse.PeriodWeatherResponse resp = new GalaxyControllerResponse.PeriodWeatherDetailResponse(weather,
                                                                            days.size(),
                                                                            detailKey,
                                                                            greaterByDetail);
        return new ResponseEntity<>(resp, OK);
    }

}