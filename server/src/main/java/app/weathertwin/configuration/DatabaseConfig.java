package app.weathertwin.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/** Configuration class for setting up the database connection in the production environment. */
@Configuration
@Profile("prod")
public class DatabaseConfig {

  /** The JDBC URL for the database connection is injected from the application properties. */
  @Value("${spring.datasource.url}")
  private String databaseURL;

  /**
   * Creates and configures a DataSource bean using HikariCP.
   *
   * @return a configured DataSource instance using HikariCP.
   */
  @Bean
  public DataSource dataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(databaseURL);
    return new HikariDataSource(config);
  }
}
