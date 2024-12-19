package app.weathertwin.service;

import java.util.HashMap;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
import java.net.URI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class HttpService {

    // API key is referenced from application properties file
    // This is not included in version control for safety reasons
    @Value("${APIkey}")
    private String API_KEY;

    // Geocoding API call to find given city's lon(gitude) and lat(itude)
    // Lon and lat are needed to make an weather API call for a specific city

    public HashMap<String, Double> fetchLatAndLon(String city) {
        HashMap<String, Double> latAndLonMap = new HashMap<String, Double>();

        final String GEOCODING_URL = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=1&appid="
                + API_KEY;

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(GEOCODING_URL))
                .GET()
                .build();

        HttpResponse<String> response;

        try {
            response = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());

            if (rootNode.isArray() && rootNode.size() > 0) {
                JsonNode firstElement = rootNode.get(0);
                latAndLonMap.put("lat", firstElement.get("lat").asDouble());
                latAndLonMap.put("lon", firstElement.get("lon").asDouble());
            } else {
                System.out.println("City not found or empty response");
            }

        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }

        return latAndLonMap;
    }
}