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
@PropertySource("classpath:application-dev.properties")
public class WeathertwinApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeathertwinApplication.class, args);
	}

	@Bean
	@Profile("!test") // does not run if test profile is active
	public CommandLineRunner weatherTwinCLR(WeatherDataRepository weatherDataRepository) {
		return (args) -> {

			System.out.println("Weather Twin is running");

			// https://www.baeldung.com/jackson-object-mapper-tutorial
			ObjectMapper objectMapper = new ObjectMapper();
			File latsAndLonsFile = new File("src\\main\\resources\\citiesLatsAndLons.json");
			JsonNode rootNode = objectMapper.readValue(latsAndLonsFile, JsonNode.class);

			while (true) {
				long startTime = System.currentTimeMillis();

				for (int i = 0; i < rootNode.size(); i++) {
					Double lat = rootNode.get(i).get("coords").get("lat").asDouble();
					Double lon = rootNode.get(i).get("coords").get("lon").asDouble();

					JsonNode cityWeatherDataJSON = HttpService.fetchWeatherData(lat, lon);
					WeatherData weatherData = ConversionService.JsonNodeToWeatherData(cityWeatherDataJSON);

					// Here we set the weather data object's city attribute from the JSON file
					// This is because the weather API does not always return the correct city name
					weatherData.setCity(rootNode.get(i).get("city").asText().replace("_", " "));

					weatherDataRepository.save(weatherData);
				}

				long endTime = System.currentTimeMillis();
				long duration = endTime - startTime;

				// Here we set the app to sleep until 1 hour has passed since the start of the
				// last loop, then repeat to get updated data
				TimeUnit.MILLISECONDS.sleep(3600000 - duration);
			}
		};
	}
}
