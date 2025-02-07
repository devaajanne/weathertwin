package app.weathertwin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "weatherdatatable")
public class WeatherData {

    /*
     * Here we se the attributes that we need for our WeatherData objects. The
     * OpenWeatherMap API returns more data as JSON, but there are the things we for
     * our application
     */
    @Id
    Long id;

    Double lat, lon;
    String city, countryCode, countryName;

    /*
     * Column name is set here because this column is used in the SQL query, so the
     * column name must be given in snake_case
     */
    @Column(name = "weather_group")
    String weatherGroup;

    Double temp;

    /* No-arg and arg constructors */
    public WeatherData() {
    }

    public WeatherData(Long id, Double lat, Double lon, String city, String countryCode, String countryName,
            Double temp,
            String weatherGroup) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.city = city;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.temp = temp;
        this.weatherGroup = weatherGroup;
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

    /* Finally, toString() method */
    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", lat='" + getLat() + "'" +
                ", lon='" + getLon() + "'" +
                ", city='" + getCity() + "'" +
                ", countryCode='" + getCountryCode() + "'" +
                ", countryName='" + getCountryName() + "'" +
                ", weatherGroup='" + getWeatherGroup() + "'" +
                ", temp='" + getTemp() + "'" +
                "}";
    }
}