package app.weathertwin.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
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

  /* No-arg and arg constructors */
  public WeatherDataResponse() {}

  public WeatherDataResponse(
      Double lat,
      Double lon,
      String city,
      String countryCode,
      String countryName,
      Double temp,
      String tempUnit,
      String weatherGroup,
      String weatherId) {
    this.lat = lat;
    this.lon = lon;
    this.city = city;
    this.countryCode = countryCode;
    this.countryName = countryName;
    this.temp = temp;
    this.tempUnit = tempUnit;
    this.weatherGroup = weatherGroup;
    this.weatherId = weatherId;
  }
}
