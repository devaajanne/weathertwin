package app.weathertwin.service;

import java.util.HashMap;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.URI;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HttpService {

    // API key is referenced from application properties file
    // This is not included in version control for safety reasons
    private static String API_KEY;

    @Value("${APIkey}")
    public void setStaticName(String name) {
        API_KEY = name;
    }

    // Geocoding API call to find given city's lon(gitude) and lat(itude)
    // Lon and lat are needed to make an weather API call for a specific city
    public static HashMap<String, Double> fetchLatAndLon(String city) {
        HashMap<String, Double> latAndLonMap = new HashMap<String, Double>();

        final String GEOCODING_URL = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=1&appid="
                + API_KEY;

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(GEOCODING_URL))
                .GET()
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());

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

    // Current weather data API call uses lon and lat data
    // We got this data through previous API call
    public static JsonNode fetchWeatherData(Double lat, Double lon) {
        JsonNode weatherData = JsonNodeFactory.instance.objectNode();

        final String WEATHERDATA_URL = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon
                + "&appid=" + API_KEY;

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(WEATHERDATA_URL))
                .GET()
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            weatherData = objectMapper.readTree(response.body());

        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }

        return weatherData;
    }
}