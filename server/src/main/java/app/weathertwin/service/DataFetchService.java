package app.weathertwin.service;

import app.weathertwin.domain.WeatherData;
import app.weathertwin.repository.WeatherDataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * This is a service class to holds a method for fetching weather data from API at 1-hour interval.
 */
@Service
@Profile("!test")
public class DataFetchService {

  private final ConversionService conversionService;
  private final HttpService httpService;
  private final LoggerService loggerService;
  private final QueryService queryService;
  private final WeatherDataRepository weatherDataRepository;

  public DataFetchService(
      ConversionService conversionService,
      HttpService httpService,
      @Qualifier("dataFetchServiceLogger") LoggerService loggerService,
      QueryService queryService,
      WeatherDataRepository weatherDataRepository) {
    this.conversionService = conversionService;
    this.httpService = httpService;
    this.loggerService = loggerService;
    this.queryService = queryService;
    this.weatherDataRepository = weatherDataRepository;
  }

  /*
   * City coordinates file path is referenced from application properties file.
   * This enables dev environment to use a lighter test set than production
   */
  private static String CITY_COORDINATES_FILE_PATH;

  @Value("${CityCoordinatesFilePath}")
  public void setStaticName(String name) {
    CITY_COORDINATES_FILE_PATH = name;
  }

  /**
   * This method clears the database every 48 hours because sometimes, OpenWeatherAPI fetches
   * weather data for the same location with a different id, resulting in database having old and
   * outdated weather data
   */
  @Scheduled(fixedRate = 172_800_000)
  public void clearDatabase() {
    weatherDataRepository.deleteAll();
  }

  /**
   * This method fetches weather data from the OpenWeatherMap.org API for the locations specified in
   * the citiesLatsAndLons.json file. The method makes one request every 1,5 seconds, and is
   * scheduled to run every hour.
   */
  @Scheduled(fixedRate = 3_600_000)
  public void fetchDataPeriodicallyFromAPI() {
    try {
      /* Source: https://www.baeldung.com/jackson-object-mapper-tutorial */
      ObjectMapper objectMapper = new ObjectMapper();
      InputStream cityCoordStream = getClass().getResourceAsStream(CITY_COORDINATES_FILE_PATH);
      JsonNode rootNode = objectMapper.readValue(cityCoordStream, JsonNode.class);

      /* We start looping the city data in the JSON file */
      for (int i = 0; i < rootNode.size(); i++) {

        /*
         * OpenWeatherMap API has a limit of 60 requests per minute on the tier used in
         * this application. We slow down our own API fetches by including a sleep
         * period of 1,5 seconds between the calls so that our API key does not get
         * blocked
         */
        Thread.sleep(1500);

        /* Here we set the lat and lon values for the http request */
        Double lat = rootNode.get(i).get("coords").get("lat").asDouble();
        Double lon = rootNode.get(i).get("coords").get("lon").asDouble();

        /*
         * We convert the fetched JSON data to a WeatherData object because the API
         * returns a lot more information than we actually need for our needs
         */
        JsonNode cityWeatherDataJSON = httpService.fetchWeatherData(lat, lon);
        WeatherData weatherData = conversionService.JsonNodeToWeatherData(cityWeatherDataJSON);

        /* We check if the city already exists in the database with a different id and remove it*/
        queryService.removeDuplicateCityFromDatabase(weatherData);

        /*
         * Here we set the weather data object's city attribute from the JSON file. This
         * is because the weather API does not always return the correct city name
         */
        weatherData.setCity(rootNode.get(i).get("city").asText().replace("_", " "));

        weatherDataRepository.save(weatherData);
        loggerService.info(
            "City nro {}: {}, {}", (i + 1), weatherData.getCity(), weatherData.getCountryCode());
      }
    } catch (InterruptedException | IOException exception) {
      exception.printStackTrace();
    }
  }
}
