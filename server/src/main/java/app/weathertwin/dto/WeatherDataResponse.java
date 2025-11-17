package app.weathertwin.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

// Serializes all fields regardless of their visibility
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor // Default constructor with no parameters
@AllArgsConstructor // Parameterized constructor
public class WeatherDataResponse {

  /*
   * Here we set the attributes that we need for our WeatherDataResponse DTO objects.
   * These are the attributes that are returned to the client via REST API.
   */
  private Double lat;
  private Double lon;
  private String city;
  private String countryCode;
  private String countryName;
  private Double temp;
  private String tempUnit;
  private String weatherGroup;
  private String weatherId;
}
