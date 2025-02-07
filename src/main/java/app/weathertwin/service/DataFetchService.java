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

@Service
@Profile("!test")
public class DataFetchService {

    private final WeatherDataRepository weatherDataRepository;

    public DataFetchService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    @Scheduled(fixedRate = 3_600_000)
    public void fetchDataPeriodicallyFromAPI() {
        try {
            /* Source: https://www.baeldung.com/jackson-object-mapper-tutorial */
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream cityCoordStream = getClass().getResourceAsStream("/citiesLatsAndLons.json");
            JsonNode rootNode = objectMapper.readValue(cityCoordStream, JsonNode.class);

            /* We start looping the city data in the JSON file */
            for (int i = 0; i < rootNode.size(); i++) {

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
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
