package app.weathertwin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** WeatherData is the main entity in this application. It stores weather data for one city. */
@Entity
@Table(name = "weatherdatatable")
@Data // Getters, setters, and toString() methods
@NoArgsConstructor // Default constructor with no parameters
@AllArgsConstructor // Parameterized constructor
public class WeatherData {

  /*
   * Here we se the attributes that we need for our WeatherData objects. The
   * OpenWeatherMap API returns more data as JSON, but there are the things we for
   * our application.
   *
   * Some column names are set here because we use an external PostgreSQL
   * database, so the column names must be given in snake_case.
   */

  @Id private Long id;

  private Double lat;
  private Double lon;
  private String city;

  @Column(name = "country_code")
  private String countryCode;

  @Column(name = "country_name")
  private String countryName;

  private Double temp;

  @Column(name = "temp_unit")
  private String tempUnit;

  @Column(name = "weather_group")
  private String weatherGroup;

  @Column(name = "weather_id")
  private String weatherId;
}
