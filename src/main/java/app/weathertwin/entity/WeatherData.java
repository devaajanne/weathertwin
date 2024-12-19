package app.weathertwin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class WeatherData {

    @Id
    Long id;
    
    String lat, lon, city;

    public WeatherData() {
    }

    public WeatherData(Long id, String lat, String lon, String city) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.city = city;
    }

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

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public WeatherData lat(String lat) {
        setLat(lat);
        return this;
    }

    public String getLon() {
        return this.lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public WeatherData lon(String lon) {
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

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", lat='" + getLat() + "'" +
                ", lon='" + getLon() + "'" +
                ", city='" + getCity() + "'" +
                "}";
    }

}
