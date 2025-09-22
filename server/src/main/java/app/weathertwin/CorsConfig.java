package app.weathertwin;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/** This configuration class holds the application's CORS config. */
@Configuration
public class CorsConfig {

  /*
   * CORS allowed origins is referenced from application properties file.
   */
  private static String CORS_ALLOWED_ORIGIN;

  @Value("${CorsAllowedOrigin}")
  public void setStaticName(String origin) {
    CORS_ALLOWED_ORIGIN = origin;
  }

  /**
   * Here we set the configuration for cross-origin resource sharing so that our front-end client
   * can send requests to our back-end server.
   *
   * @return a CorsConfigurationSource object configured with the specified CORS settings.
   */
  @Bean
  public CorsConfigurationSource getCorsConfigurationSource() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowedOrigins(List.of(CORS_ALLOWED_ORIGIN));
    corsConfig.setAllowedMethods(List.of("POST", "OPTIONS")); // OPTIONS is needed due to pre-flight
    corsConfig.setAllowedHeaders(List.of("Content-Type"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return source;
  }
}
