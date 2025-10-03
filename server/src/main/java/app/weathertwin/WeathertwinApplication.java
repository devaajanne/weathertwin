package app.weathertwin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/** The main entry point for the Weather Twin Spring Boot application. */
@SpringBootApplication
@EnableScheduling
public class WeathertwinApplication {

  /**
   * The entry point of the Weather Twin application.
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(String[] args) {
    SpringApplication.run(WeathertwinApplication.class, args);
  }
}
