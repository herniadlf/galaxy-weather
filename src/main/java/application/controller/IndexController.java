package application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

//    private GalaxyProperties galaxyProperties;
//
//    @Autowired
//    public void setGalaxyProps(GalaxyProperties galaxyProperties) {
//        this.galaxyProperties = galaxyProperties;
//    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Galaxy Weather!";
    }

}