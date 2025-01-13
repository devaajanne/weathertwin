package app.weathertwin.service;

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
    // https://www.baeldung.com/spring-inject-static-field
    private static String OPEN_WEATHER_API_KEY;

    @Value("${OpenWeatherAPIkey}")
    public void setStaticName(String name) {
        OPEN_WEATHER_API_KEY = name;
    }

    // Current weather data API call uses lon and lat data
    // We got this data through a library in the client,
    // passed to server through http body
    public static JsonNode fetchWeatherData(Double lat, Double lon) {
        JsonNode weatherData = JsonNodeFactory.instance.objectNode();

        final String WEATHERDATA_URL = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon
                + "&appid=" + OPEN_WEATHER_API_KEY;

        // https://www.baeldung.com/java-9-http-client
        // https://www.baeldung.com/java-uri-create-and-new-uri
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(WEATHERDATA_URL))
                .GET()
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());

            // https://www.baeldung.com/jackson-object-mapper-tutorial
            ObjectMapper objectMapper = new ObjectMapper();
            weatherData = objectMapper.readTree(response.body());

        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }

        return weatherData;
    }
}