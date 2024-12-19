package app.weathertwin.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.weathertwin.service.HttpService;

@RestController
@RequestMapping("api/weather")
public class WeathertwinController {

    private HttpService httpService;

    public WeathertwinController(HttpService httpService) {
        this.httpService = httpService;
    }

    @GetMapping("/{city}")
    public JsonNode getCityWeatherData(@PathVariable String city) {
        HashMap<String, Double> latAndLonMap = new HashMap<String, Double>();
        JsonNode cityWeatherData = JsonNodeFactory.instance.objectNode();

        latAndLonMap = httpService.fetchLatAndLon(city);
        cityWeatherData = httpService.fetchWeatherData(latAndLonMap.get("lat"), latAndLonMap.get("lon"));

        return cityWeatherData;
    }
}
