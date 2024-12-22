package app.weathertwin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class WeatherData {

    @Id
    Integer id;

    Double lat, lon;
    String city, countryCode;
    Double temp;

    public WeatherData() {
    }

    public WeatherData(Integer id, Double lat, Double lon, String city, String countryCode, Double temp) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.city = city;
        this.countryCode = countryCode;
        this.temp = temp;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public WeatherData id(Integer id) {
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

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", lat='" + getLat() + "'" +
            ", lon='" + getLon() + "'" +
            ", city='" + getCity() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", temp='" + getTemp() + "'" +
            "}";
    }
    

}