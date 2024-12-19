package app.weathertwin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-dev.properties")
public class WeathertwinApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeathertwinApplication.class, args);
		System.out.println("Weather Twin is running");
	}

}
