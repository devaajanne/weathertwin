package app.weathertwin.controller;

import app.weathertwin.entity.WeatherData;
import app.weathertwin.service.ConversionService;
import app.weathertwin.service.HttpService;
import app.weathertwin.service.QueryService;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** This is the REST API controller for the application */
@RestController
@RequestMapping("/api")
public class WeathertwinController {

  private final QueryService queryService;

  public WeathertwinController(QueryService queryService) {
    this.queryService = queryService;
  }

  /**
   * REST API endpoint that handles receiving user's input from the client, querying for cities with
   * similar weather and returning the results
   *
   * @param requestBody JSON object for the input city sent from client
   * @return HashMap with two entries: input location WeatherData, and similar location WeatherData
   */
  @PostMapping("/weatherdata")
  public HashMap<String, WeatherData> getCityWeatherData(@RequestBody JsonNode requestBody) {
    /*
     * We initialize a new HashMap to store
     * 1) user's input location's weather data and
     * 2) a similar location's weather data
     * This map is returned to the client.
     * We also store the user's selected unit (metric/imperial) in to variable
     */
    HashMap<String, WeatherData> returnedMap = new HashMap<String, WeatherData>();
    String targetUnit = requestBody.get("unit").asText();

    /*
     * We first find the weather data for the user's input location and convert JSON
     * data from OpenWeatherMap API into a WeatherData object.
     */
    JsonNode cityWeatherDataJSON =
        HttpService.fetchWeatherData(
            requestBody.get("cityCoords").get("lat").asDouble(),
            requestBody.get("cityCoords").get("lon").asDouble());
    WeatherData inputLocationData = ConversionService.JsonNodeToWeatherData(cityWeatherDataJSON);

    /*
     * Then we collect locations with similar weather to a list using our SQL query
     */
    List<WeatherData> similarWeatherDataList =
        queryService.findSimilarWeatherDataFromrepository(inputLocationData);

    /*
     * By default, the client sends the input location's city's name in the request
     * body, so we set this as the WeatherData object's cityName. This is because
     * sometimes, OpenWeatherMap API gets the city names wrong for locations that
     * are found with lat and lon data. We also set the correct country name based
     * on the country code, and store the input location's weather data into the map
     * that we will return to the client
     */
    inputLocationData.setCity((requestBody.get("cityName").asText().split(","))[0]);
    inputLocationData.setCountryName(
        queryService.findCountryNameByCountryCode(inputLocationData.getCountryCode()));
    returnedMap.put("inputLocation", ConversionService.convertTemp(inputLocationData, targetUnit));

    /*
     * Random is initialized because we want to get a random location from the list
     * of similar locations
     */
    Random random = new Random();

    /*
     * If no similar weather locations are found, we store the value "null" as the
     * similar location's weather data in to the returned map.
     * If similar weather locations are found, we select a random location, set the
     * correct country name and temperature unit and store it into the returned map
     */
    if (similarWeatherDataList.size() == 0) {
      returnedMap.put("similarLocation", null);
    } else {
      WeatherData similarLocationData =
          similarWeatherDataList.get(random.nextInt(similarWeatherDataList.size()));
      similarLocationData.setCountryName(
          queryService.findCountryNameByCountryCode(similarLocationData.getCountryCode()));
      returnedMap.put(
          "similarLocation", ConversionService.convertTemp(similarLocationData, targetUnit));
    }

    /*
     * Finally, we return the HashMap with the input and similar locations' weather
     * data to the client
     */
    return returnedMap;
  }
}
