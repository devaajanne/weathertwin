package app.weathertwin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import app.weathertwin.entity.WeatherData;

public interface WeatherDataRepository extends CrudRepository<WeatherData, Long> {

    // https://www.baeldung.com/spring-data-jpa-query
    @Query(value = "SELECT * FROM weatherdatatable WHERE temp BETWEEN :minTemp AND :maxTemp AND id != :id", nativeQuery = true)
    List<WeatherData> findWeatherDataThatMeetsConditions(
            @Param("minTemp") Double minTemp,
            @Param("maxTemp") Double maxTemp,
            @Param("id") Long id);
}
