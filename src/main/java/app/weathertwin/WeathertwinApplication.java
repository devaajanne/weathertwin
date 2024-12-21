package app.weathertwin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import app.weathertwin.repository.WeatherDataRepository;

@SpringBootApplication
@PropertySource("classpath:application-dev.properties")
public class WeathertwinApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeathertwinApplication.class, args);
	}

	@Bean
	public CommandLineRunner weatherTwinCLR(WeatherDataRepository weatherDataRepository) {
		return (args) -> {

			System.out.println("Weather Twin is running");

			// TODO: add test lats and lons here, fetch the weather data with a loop (?),
			// convert the data with DTO to an entity, save to database
		};
	}
}
