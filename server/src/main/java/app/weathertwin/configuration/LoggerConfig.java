package app.weathertwin.configuration;

import app.weathertwin.service.LoggerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

  @Bean(name = "dataFetchServiceLogger")
  public LoggerService dataFetchServiceLogger() {
    return new LoggerService("DataFetchServiceLogger");
  }
}
