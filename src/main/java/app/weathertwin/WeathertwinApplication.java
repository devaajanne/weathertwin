package app.weathertwin;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.weathertwin.entity.WeatherData;
import app.weathertwin.repository.WeatherDataRepository;
import app.weathertwin.service.ConversionService;
import app.weathertwin.service.HttpService;

@SpringBootApplication
public class WeathertwinApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeathertwinApplication.class, args);
	}

	@Bean
	@Profile("!test") // does not run if test profile is active
	public CommandLineRunner weatherTwinCLR(WeatherDataRepository weatherDataRepository) {
		return (args) -> {

			System.out.println("Weather Twin is running");

			/* Source: https://www.baeldung.com/jackson-object-mapper-tutorial */
			ObjectMapper objectMapper = new ObjectMapper();
			File latsAndLonsFile = new File("src\\main\\resources\\citiesLatsAndLons.json");
			JsonNode rootNode = objectMapper.readValue(latsAndLonsFile, JsonNode.class);

			/* Here we start fetching weather data into our database */
			while (true) {

				/* We log the start time */
				long startTime = System.currentTimeMillis();

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
				}

				/*
				 * We log the end time and calculate how long it took to get all the data
				 * through the OpenWeatherMapAPI
				 */
				long endTime = System.currentTimeMillis();
				long duration = endTime - startTime;

				/*
				 * Here we set the app to sleep until 1 hour has passed since the start of the
				 * last loop, then repeat to get updated data
				 */
				TimeUnit.MILLISECONDS.sleep(3600000 - duration);
			}
		};
	}
}
