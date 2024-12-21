package app.weathertwin.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;

import app.weathertwin.entity.WeatherData;
import app.weathertwin.service.HttpService;
import app.weathertwin.service.ConversionService;

@RestController
@RequestMapping("api/weatherdata")
public class WeathertwinController {

    private HttpService httpService;

    public WeathertwinController(HttpService httpService) {
        this.httpService = httpService;
    }

    @GetMapping
    public HashMap<String, WeatherData> getCityWeatherData(@RequestBody JsonNode requestBody) {
        HashMap<String, WeatherData> returnedMap = new HashMap<String, WeatherData>();

        HashMap<String, Double> latAndLonMap = httpService.fetchLatAndLon(requestBody.get("city").asText());
        JsonNode cityWeatherDataJSON = httpService.fetchWeatherData(
                latAndLonMap.get("lat"),
                latAndLonMap.get("lon"));

        WeatherData inputLocationData = ConversionService.JsonNodeToWeatherData(cityWeatherDataJSON, requestBody.get("unit").asText());
        // TODO: implement logic/call method to find a city with similar weather

        returnedMap.put("inputLocation", inputLocationData);
        returnedMap.put("similarLocation", null);

        return returnedMap;
    }
}
