package com.sezer.earthquake;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    Service service = new Service();

    @GetMapping("/")
    public String index() {
        return "Go to localhost:8080/10 where 10 is number of days for earthquake search";
    }
    @GetMapping("/{numberOfDays}")
    public String getEarthquakeController(@PathVariable("numberOfDays") int numberOfDays) {
        String apiResponse = service.getEarthquakeService(numberOfDays);
        return apiResponse;
    }
}
