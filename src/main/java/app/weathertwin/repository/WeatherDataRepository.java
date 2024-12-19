package app.weathertwin.repository;

import org.springframework.data.repository.CrudRepository;

import app.weathertwin.entity.WeatherData;

public interface WeatherDataRepository extends CrudRepository<WeatherData, Long> {

}
