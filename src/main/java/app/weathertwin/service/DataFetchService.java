package app.weathertwin.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.weathertwin.entity.WeatherData;
import app.weathertwin.repository.WeatherDataRepository;

/**
 * This is a service class to holds a method for fetching weather data from API at 1-hour interval.
 */
@Service
@Profile("!test")
public class DataFetchService {

    private final WeatherDataRepository weatherDataRepository;

    public DataFetchService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    /**
     * This method fetches weather data from the OpenWeatherMap.org API for the locations specified in the citiesLatsAndLons.json file.
     * The method makes one request every 1,5 seconds, and is scheduled to run every hour.
     */
    @Scheduled(fixedRate = 3_600_000)
    public void fetchDataPeriodicallyFromAPI() {
        try {
            /* Source: https://www.baeldung.com/jackson-object-mapper-tutorial */
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream cityCoordStream = getClass().getResourceAsStream("/citiesLatsAndLons.json");
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
                JsonNode cityWeatherDataJSON = HttpService.fetchWeatherData(lat, lon);
                WeatherData weatherData = ConversionService.JsonNodeToWeatherData(cityWeatherDataJSON);

                /*
                 * Here we set the weather data object's city attribute from the JSON file. This
                 * is because the weather API does not always return the correct city name
                 */
                weatherData.setCity(rootNode.get(i).get("city").asText().replace("_", " "));

                weatherDataRepository.save(weatherData);
                System.out.println("City nro " + (i + 1));
            }
        } catch (InterruptedException | IOException exception) {
            exception.printStackTrace();
        }
    }
}
