package app.weathertwin.service;

import app.weathertwin.domain.WeatherData;
import app.weathertwin.dto.WeatherDataResponse;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class ControllerService {

  private final ConversionService conversionService;
  private final HttpService httpService;
  private final QueryService queryService;
  private final MapperService mapperService;

  public ControllerService(
      ConversionService conversionService,
      HttpService httpService,
      QueryService queryService,
      MapperService mapperService) {
    this.conversionService = conversionService;
    this.httpService = httpService;
    this.queryService = queryService;
    this.mapperService = mapperService;
  }

  public HashMap<String, WeatherDataResponse> getWeatherTwinData(
      String city, double lat, double lon, String unit) {
    /*
     * We initialize a new HashMap to store
     * 1) user's input location's weather data and
     * 2) a similar location's weather data
     * This map is returned to the client.
     * We also store the user's selected unit (metric/imperial) in to variable
     */
    HashMap<String, WeatherDataResponse> responseMap = new HashMap<String, WeatherDataResponse>();

    /*
     * We first find the weather data for the user's input location and convert JSON
     * data from OpenWeatherMap API into a WeatherData object.
     */
    JsonNode cityWeatherDataJSON = httpService.fetchWeatherData(lat, lon);
    WeatherData inputLocationData = conversionService.JsonNodeToWeatherData(cityWeatherDataJSON);

    /*
     * Then we collect locations with similar weather to a list using our SQL query
     */
    List<WeatherData> similarWeatherDataList =
        queryService.findSimilarWeatherDataFromRepository(inputLocationData);

    /*
     * By default, the client sends the input location's city's name in the request
     * param, so we set this as the WeatherData object's cityName. This is because
     * sometimes, OpenWeatherMap API gets the city names wrong for locations that
     * are found with lat and lon data. We also set the correct country name based
     * on the country code, and map and store the input location's weather data into
     * the response map that we will return to the client.
     */
    inputLocationData.setCity((city.split(","))[0]);
    inputLocationData.setCountryName(
        queryService.findCountryNameByCountryCode(inputLocationData.getCountryCode()));
    responseMap.put(
        "inputLocation",
        mapperService.toWeatherDataResponse(
            conversionService.convertTemp(inputLocationData, unit)));

    /*
     * Random is initialized because we want to get a random location from the list
     * of similar locations
     */
    Random random = new Random();

    /*
     * If no similar weather locations are found, we store the value "null" as the
     * similar location's weather data in to the response map.
     * If similar weather locations are found, we select a random location, set the
     * correct country name and temperature unit and map and store it into the response map
     */
    if (similarWeatherDataList.isEmpty()) {
      responseMap.put("similarLocation", null);
    } else {
      WeatherData similarLocationData =
          similarWeatherDataList.get(random.nextInt(similarWeatherDataList.size()));
      similarLocationData.setCountryName(
          queryService.findCountryNameByCountryCode(similarLocationData.getCountryCode()));
      responseMap.put(
          "similarLocation",
          mapperService.toWeatherDataResponse(
              conversionService.convertTemp(similarLocationData, unit)));
    }

    /*
     * Finally, we return the HashMap with the input and similar locations' weather
     * data to the controller
     */
    return responseMap;
  }
}
