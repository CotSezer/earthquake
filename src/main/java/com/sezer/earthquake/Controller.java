package com.sezer.earthquake;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    Service service = new Service();

    /**
     * Handles the root URL request and provides instructions on how to use the API.
     *
     * @return A string message with instructions on how to use the API.
     */
    @GetMapping("/")
    public String index() {
        return "Go to localhost:8080/hawaii/10 to get a list of earthquakes happened in Hawaii in last 10 days.";
    }

    /**
     * Retrieves earthquake information for a specific country within a given number of days.
     *
     * @param country      The country to filter the earthquakes by.
     * @param numberOfDays The number of days before the current date to search for earthquakes.
     * @return             A JSON string containing earthquake information for the specified country.
     */
    @GetMapping("/{country}/{numberOfDays}")
    public String getEarthquakeController(@PathVariable("country") String country,
                                          @PathVariable("numberOfDays") int numberOfDays) {
        String apiResponse = service.getEarthquakeService(country, numberOfDays);
        return apiResponse;
    }
}
