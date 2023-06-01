package com.sezer.earthquake;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Date;

/**
 * Service class for retrieving earthquake information.
 */
public class Service {
    /**
     * Retrieves earthquake information for a specific country within a given number of days before the current date.
     *
     * @param country     The country to filter the earthquakes by.
     * @param daysBefore  The number of days before the current date to search for earthquakes.
     * @return            A JSON array containing earthquake information for the specified country.
     */
    public String getEarthquakeService(String country, int daysBefore) {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateBefore = currentDate.minusDays(daysBefore);

        String apiUrlFormat = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s";
        String apiUrl = String.format(apiUrlFormat, dateBefore, currentDate);
        String responseString = makeExternalRequest(apiUrl);
        JSONObject jsonObject = new JSONObject(responseString);
        JSONArray earthquakes = getEarthquakeFromResponseJSON(jsonObject, country);
        if (earthquakes.isEmpty()) {
            return String.format("No Earthquakes were recorded past %d days", daysBefore);
        }
        return earthquakes.toString();
    }

    /**
     * Extracts earthquake information for a specific country from the provided JSON object.
     *
     * @param json          The JSON object containing earthquake data.
     * @param inputCountry  The country to filter the earthquakes by.
     * @return              A JSON array containing earthquake information for the specified country.
     */
    private JSONArray getEarthquakeFromResponseJSON(JSONObject json, String inputCountry) {
        JSONArray features = json.getJSONArray("features");
        JSONArray earthquakeArray = new JSONArray();

        for (int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i);
            JSONObject properties = feature.getJSONObject("properties");
            JSONObject earthquake = new JSONObject();
            earthquake.put("Magnitude", properties.get("mag"));
            long timestamp = properties.getLong("time");
            Date date = new Date(timestamp);
            earthquake.put("Date", date);
            Object place = properties.get("place");
            earthquake.put("Location", place);
            if (place != null) {
                String earthquakeCountry = place.toString().replaceAll(".*,(.*)", "$1").strip();
                if (earthquakeCountry.equalsIgnoreCase(inputCountry)) {
                    earthquake.put("Country", earthquakeCountry);
                    earthquakeArray.put(earthquake);
                }
            }
        }

        return earthquakeArray;
    }

    /**
     * Makes an external request to the specified URL and retrieves the response as a string.
     *
     * @param url  The URL to make the request to.
     * @return     The response body as a string.
     * @throws RuntimeException if the external request fails.
     */
    private String makeExternalRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("External request failed with status code: " + response.getStatusCodeValue());
        }
    }
}
