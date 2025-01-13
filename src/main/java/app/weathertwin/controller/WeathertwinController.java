package app.weathertwin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;

import app.weathertwin.entity.WeatherData;
import app.weathertwin.service.HttpService;
import app.weathertwin.service.ConversionService;
import app.weathertwin.service.QueryService;

@RestController
@RequestMapping("api")
public class WeathertwinController {

    private final QueryService queryService;

    public WeathertwinController(QueryService queryService) {
        this.queryService = queryService;
    }

    @PostMapping("/weatherdata")
    public HashMap<String, WeatherData> getCityWeatherData(@RequestBody JsonNode requestBody) {
        HashMap<String, WeatherData> returnedMap = new HashMap<String, WeatherData>();
        
        JsonNode cityWeatherDataJSON = HttpService.fetchWeatherData(
                requestBody.get("cityCoords").get("lat").asDouble(),
                requestBody.get("cityCoords").get("lon").asDouble());
        
        String targetUnit = requestBody.get("unit").asText();

        WeatherData inputLocationData = ConversionService.JsonNodeToWeatherData(cityWeatherDataJSON);
        inputLocationData.setCity((requestBody.get("cityName").asText().split(","))[0]);

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
}
