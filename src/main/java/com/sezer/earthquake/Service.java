package com.sezer.earthquake;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Date;

public class Service {
    public String getEarthquakeService(int daysBefore) {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateBefore = currentDate.minusDays(daysBefore);

        String apiUrlFormat = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s";
        String apiUrl = String.format(apiUrlFormat, dateBefore, currentDate);
        String responseString =  makeExternalRequest(apiUrl);
        JSONObject jsonObject = new JSONObject(responseString);
        JSONArray earthquakes = getEarthquakeFromResponseJSON(jsonObject);
        return earthquakes.toString();
    }

    private JSONArray getEarthquakeFromResponseJSON(JSONObject json) {
        JSONArray features = json.getJSONArray("features");
        JSONArray earthquakeArray = new JSONArray();

        for (int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i);
            JSONObject properties = feature.getJSONObject("properties");
            JSONObject earthquake = new JSONObject();
            earthquake.put("Magnitude", properties.get("mag"));
            System.out.println(properties);
            earthquake.put("Location", properties.get("place"));
            long timestamp = properties.getLong("time");
            Date date = new Date(timestamp);
            earthquake.put("Date", date);
            earthquakeArray.put(earthquake);
        }

        return earthquakeArray;
    }

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
