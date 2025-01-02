package app.weathertwin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import app.weathertwin.entity.WeatherData;

public interface WeatherDataRepository extends CrudRepository<WeatherData, Long> {

    // https://www.baeldung.com/spring-data-jpa-query

    // Query explained:
    // 1) we want to find locations with similar temperatures, so temp must be
    // WITHIN certain parameters so that the temperature differences are not too big
    //
    // 2) we want to find locations that are not right next to each other, so lon
    // and lat must be OUTSIDE certain parameters to that the locations are
    // sufficiently far away from each other
    //
    // 3) we want to find locations that are experiencing the same kind of weather,
    // so weather groups must match
    //
    // 4) we don't want to return the input location, so we check that the ids in
    // the input location and found similar location are not the same
    @Query(value = "SELECT * FROM weatherdatatable WHERE (temp BETWEEN :minTemp AND :maxTemp) AND (lon NOT BETWEEN :minLon AND :maxLon) AND (lat NOT BETWEEN :minLat AND :maxLat) AND (weather_group = :weatherGroup) AND (id != :id)", nativeQuery = true)
    List<WeatherData> findWeatherDataThatMeetsConditions(
            @Param("minTemp") Double minTemp,
            @Param("maxTemp") Double maxTemp,
            @Param("minLon") Double minLon,
            @Param("maxLon") Double maxLon,
            @Param("minLat") Double minLat,
            @Param("maxLat") Double maxLat,
            @Param("weatherGroup") String weatherGroup,
            @Param("id") Long id);
}
