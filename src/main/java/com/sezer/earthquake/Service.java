package com.sezer.earthquake;

import com.sezer.earthquake.model.Boundary;
import com.sezer.earthquake.model.Data;
import com.sezer.earthquake.model.FinalResponse;
import com.sezer.earthquake.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service class for retrieving earthquake information.
 */
public class Service {

    List<Boundary> countryBoundaries;

    /**
     * Retrieves earthquake information for a specific country within a given number of days before the current date.
     *
     * @param country     The country to filter the earthquakes by.
     * @param daysBefore  The number of days before the current date to search for earthquakes.
     * @return            A JSON array containing earthquake information for the specified country.
     */
    public FinalResponse getEarthquakeService(String country, int daysBefore) {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateBefore = currentDate.minusDays(daysBefore);
        FinalResponse finalResponse = new FinalResponse();

        if(countryBoundaries == null) {
            populateBoundaries();
        }

        Optional<Boundary> countryFound = countryBoundaries.stream().filter(boundary -> boundary.getCountry().equalsIgnoreCase(country)).findAny();

        if(!countryFound.isPresent()) {
            finalResponse.setStatus("FAILURE");
            finalResponse.setMessage("Country not found!");
            return finalResponse;
        }

        String apiUrlFormat = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s&maxlatitude=%s&minlatitude=%s&maxlongitude=%s&minlongitude=%s";
        String apiUrl = String.format(apiUrlFormat, dateBefore, currentDate, countryFound.get().getMaxLatitude(), countryFound.get().getMinLatitude(),
                countryFound.get().getMaxLongitude(), countryFound.get().getMinLongitude());
        ResponseEntity<Response> response = makeExternalRequest(apiUrl);

        if (!response.getStatusCode().is2xxSuccessful()) {
            finalResponse.setStatus("FAILURE");
            finalResponse.setMessage(String.format("No Earthquakes were recorded past %d days", daysBefore));
            return finalResponse;
        }

        //Further filter the response to remove unwanted countries which might lie near the end of our search boundary
        finalResponse.setResponses(response.getBody().getFeatures().stream().filter(f -> f.getProperties().getPlace() != null && f.getProperties().getPlace().toLowerCase().contains(country))
                .map(f -> {
                    Data data = new Data();
                    data.setCountry(country);
                    data.setMagnitude(String.valueOf(f.getProperties().getMag()));
                    data.setDate(dateBefore.toString());
                    data.setLocation(f.getProperties().getPlace());
                    return data;
                }).collect(Collectors.toList()));
        finalResponse.setStatus("SUCCESS");

        return finalResponse;
    }

    /**
     * Makes an external request to the specified URL and retrieves the response as a string.
     *
     * @param url  The URL to make the request to.
     * @return     The response body as a string.
     * @throws RuntimeException if the external request fails.
     */
    private ResponseEntity<Response> makeExternalRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Response> response = restTemplate.getForEntity(url, Response.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("External request failed with status code: " + response.getStatusCodeValue());
        }

        return response;
    }

    /**
     * Populates the static boundary data to a list
     */
    private void populateBoundaries() {
        String fileName = "src/main/resources/latlng.txt";

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            countryBoundaries = stream
                    .skip(1L)
                    .map(line -> {
                        String[] data = line.split(",");
                        Boundary boundary = new Boundary(data[0], Float.parseFloat(data[1]), Float.parseFloat(data[2]),
                                    Float.parseFloat(data[3]), Float.parseFloat(data[4]));
                        return boundary;
                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
