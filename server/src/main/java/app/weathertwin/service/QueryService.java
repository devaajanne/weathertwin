package app.weathertwin.service;

import app.weathertwin.entity.WeatherData;
import app.weathertwin.repository.WeatherDataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.stereotype.Service;

/** This is a service class to hold methods for querying and finding matching data */
@Service
public class QueryService {

  private final WeatherDataRepository weatherDataRepository;

  public QueryService(WeatherDataRepository weatherDataRepository) {
    this.weatherDataRepository = weatherDataRepository;
  }

  /**
   * This is a method for setting search criteria for querying the cities with matching weather from
   * the database
   *
   * @param inputWeatherData a WeatherData object for the input city
   * @return a list of criteria matching WeatherData objects
   */
  public List<WeatherData> findSimilarWeatherDataFromrepository(WeatherData inputWeatherData) {
    /*
     * Input and similar location temperatures must be no more than 1 kelvin from
     * one another
     */
    Double minTemp = inputWeatherData.getTemp() - 0.5;
    Double maxTemp = inputWeatherData.getTemp() + 0.5;

    /* Input and similar locations must have enough distance between them */
    Double minLon = inputWeatherData.getLon() - 10.0;
    Double maxLon = inputWeatherData.getLon() + 10.0;
    Double minLat = inputWeatherData.getLat() - 10.0;
    Double maxLat = inputWeatherData.getLat() + 10.0;

    /*
     * Input and similar locations must have a similar kind of weather (i.e. sunny, rainy, cloudy etc.)
     */
    String weatherGroup = inputWeatherData.getWeatherGroup();

    /* Id is used to exclude the input location from query results */
    Long id = inputWeatherData.getId();

    return weatherDataRepository.findWeatherDataThatMeetsConditions(
        minTemp, maxTemp, minLon, maxLon, minLat, maxLat, weatherGroup, id);
  }

  /**
   * By default, we store WeatherData objects with the country code and without the country name.
   * Only when the objects are sent to the client do we set the country name based on the country
   * code. This makes the app run more smoothly.
   *
   * @param countryCode city's country code
   * @return country name matching the country code
   */
  public String findCountryNameByCountryCode(String countryCode) {
    String countryName = countryCode;

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      InputStream countryCodesAndNamesStream =
          getClass().getResourceAsStream("/ISO3166_CountryCodesAndNames.json");
      JsonNode rootNode = objectMapper.readTree(countryCodesAndNamesStream);
      countryName = rootNode.get(countryCode).asText();
    } catch (IOException exception) {
      exception.printStackTrace();
    }

    return countryName;
  }
}
