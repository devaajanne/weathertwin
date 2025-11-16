package app.weathertwin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** WeatherData is the main entity in this application. It stores weather data for one city. */
@Entity
@Table(name = "weatherdatatable")
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

  /* No-arg and arg constructors */
  public WeatherData() {}

  public WeatherData(
      Long id,
      Double lat,
      Double lon,
      String city,
      String countryCode,
      String countryName,
      Double temp,
      String tempUnit,
      String weatherGroup,
      String weatherId) {
    this.id = id;
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

  /* Getters and setters */

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public WeatherData id(Long id) {
    setId(id);
    return this;
  }

  public Double getLat() {
    return this.lat;
  }

  public void setLat(Double lat) {
    this.lat = lat;
  }

  public WeatherData lat(Double lat) {
    setLat(lat);
    return this;
  }

  public Double getLon() {
    return this.lon;
  }

  public void setLon(Double lon) {
    this.lon = lon;
  }

  public WeatherData lon(Double lon) {
    setLon(lon);
    return this;
  }

  public Double getTemp() {
    return this.temp;
  }

  public void setTemp(Double temp) {
    this.temp = temp;
  }

  public WeatherData temp(Double temp) {
    setTemp(temp);
    return this;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public WeatherData city(String city) {
    setCity(city);
    return this;
  }

  public String getCountryCode() {
    return this.countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public WeatherData countryCode(String countryCode) {
    setCountryCode(countryCode);
    return this;
  }

  public String getCountryName() {
    return this.countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public WeatherData countryName(String countryName) {
    setCountryName(countryName);
    return this;
  }

  public String getTempUnit() {
    return this.tempUnit;
  }

  public void setTempUnit(String tempUnit) {
    this.tempUnit = tempUnit;
  }

  public WeatherData tempUnit(String tempUnit) {
    setTempUnit(tempUnit);
    return this;
  }

  public String getWeatherId() {
    return this.weatherId;
  }

  public void setWeatherId(String weatherId) {
    this.weatherId = weatherId;
  }

  public WeatherData weatherId(String weatherId) {
    setWeatherId(weatherId);
    return this;
  }

  public String getWeatherGroup() {
    return this.weatherGroup;
  }

  public void setWeatherGroup(String weatherGroup) {
    this.weatherGroup = weatherGroup;
  }

  public WeatherData weatherGroup(String weatherGroup) {
    setWeatherGroup(weatherGroup);
    return this;
  }

  /* Finally, toString() method */
  @Override
  public String toString() {
    return "{"
        + " id='"
        + getId()
        + "'"
        + ", lat='"
        + getLat()
        + "'"
        + ", lon='"
        + getLon()
        + "'"
        + ", temp='"
        + getTemp()
        + "'"
        + ", city='"
        + getCity()
        + "'"
        + ", countryCode='"
        + getCountryCode()
        + "'"
        + ", countryName='"
        + getCountryName()
        + "'"
        + ", tempUnit='"
        + getTempUnit()
        + "'"
        + ", weatherId='"
        + getWeatherId()
        + "'"
        + ", weatherGroup='"
        + getWeatherGroup()
        + "'"
        + "}";
  }
}
