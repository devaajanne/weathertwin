package app.weathertwin.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;

import app.weathertwin.service.HttpService;

@RestController
@RequestMapping("api/weather")
public class WeathertwinController {

    private HttpService httpService;

    public WeathertwinController(HttpService httpService) {
        this.httpService = httpService;
    }

    @GetMapping("/{city}")
    public HashMap<String, JsonNode> getCityWeatherData(@PathVariable String city) {
        HashMap<String, JsonNode> returnedMap = new HashMap<String, JsonNode>();

        HashMap<String, Double> latAndLonMap = httpService.fetchLatAndLon(city);
        JsonNode cityWeatherData = httpService.fetchWeatherData(latAndLonMap.get("lat"), latAndLonMap.get("lon"));

        // TODO: implement logic/call method to find a city with similar weather

        returnedMap.put("inputLocation", cityWeatherData);
        returnedMap.put("similarLocation", null);

        return returnedMap;
    }
}
