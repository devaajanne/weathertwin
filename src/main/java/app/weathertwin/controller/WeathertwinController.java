package app.weathertwin.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.weathertwin.entity.WeatherData;
import app.weathertwin.repository.WeatherDataRepository;
import app.weathertwin.service.HttpService;
import app.weathertwin.service.ConversionService;
import app.weathertwin.service.QueryService;

@RestController
@RequestMapping("api")
public class WeathertwinController {

    private final WeatherDataRepository weatherDataRepository;
    private final QueryService queryService;

    public WeathertwinController(WeatherDataRepository weatherDataRepository, QueryService queryService) {
        this.weatherDataRepository = weatherDataRepository;
        this.queryService = queryService;
    }

    @GetMapping("/weatherdata")
    public HashMap<String, WeatherData> getCityWeatherData(@RequestBody JsonNode requestBody) {
        HashMap<String, WeatherData> returnedMap = new HashMap<String, WeatherData>();
        String inputCity = requestBody.get("city").asText().replace(" ", "_");
        String targetUnit = requestBody.get("unit").asText();

        HashMap<String, Double> latAndLonMap = HttpService.fetchLatAndLon(inputCity);
        JsonNode cityWeatherDataJSON = HttpService.fetchWeatherData(
                latAndLonMap.get("lat"),
                latAndLonMap.get("lon"));

        WeatherData inputLocationData = ConversionService.JsonNodeToWeatherData(cityWeatherDataJSON);
        inputLocationData.setCity(inputCity.replace("_", " "));

        List<WeatherData> similarWeatherDataList = queryService.findSimilarWeatherDataFromrepository(inputLocationData);

        Random random = new Random();

        returnedMap.put("inputLocation", ConversionService.convertTemp(inputLocationData, targetUnit));

        if (similarWeatherDataList.size() == 0) {
            returnedMap.put("similarLocation", null);
        } else {
            returnedMap.put("similarLocation", ConversionService.convertTemp(
                    similarWeatherDataList.get(random.nextInt(similarWeatherDataList.size())), targetUnit));
        }

        return returnedMap;
    }

    private static String API_KEY;

    @Value("${APIkey}")
    public void setStaticName(String name) {
        API_KEY = name;
    }

    // Endpoint for testing returned geocoding data as json
    // Not meant for production
    @GetMapping("/geocoding")
    public JsonNode getCityGeocoding(@RequestBody JsonNode requestBody) {
        JsonNode rootNode = null;

        String city = requestBody.get("city").asText();

        final String GEOCODING_URL = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=5&appid="
                + API_KEY;

        // https://www.baeldung.com/java-9-http-client
        // https://www.baeldung.com/java-uri-create-and-new-uri
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(GEOCODING_URL))
                .GET()
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());

            // https://www.baeldung.com/jackson-object-mapper-tutorial
            ObjectMapper objectMapper = new ObjectMapper();
            rootNode = objectMapper.readTree(response.body());

        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }

        return rootNode;
    }

    // Endpoint for testing returned weather data as json
    // Not meant for production
    @GetMapping("/weatherdatajson")
    public JsonNode getWeatherDataJson(@RequestBody JsonNode requestBody) {
        JsonNode rootNode = null;

        String lat = requestBody.get("lat").asText();
        String lon = requestBody.get("lon").asText();

        final String WEATHERDATA_URL = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon
                + "&appid=" + API_KEY;

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
            rootNode = objectMapper.readTree(response.body());

        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }

        return rootNode;
    }

    @GetMapping("/findbyid")
    public WeatherData findById(@RequestBody JsonNode requestBody) {
        Long id = requestBody.get("id").asLong();

        return (weatherDataRepository.findById(id).get());
    }

    @GetMapping("/findall")
    public Iterable<WeatherData> findAll() {
        return (weatherDataRepository.findAll());
    }
}
