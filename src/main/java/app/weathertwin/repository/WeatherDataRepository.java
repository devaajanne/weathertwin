package app.weathertwin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import app.weathertwin.entity.WeatherData;

/**
 * This is the repository that stores WeatherData objects
 */
public interface WeatherDataRepository extends CrudRepository<WeatherData, Long> {

    /*
     * Source: https://www.baeldung.com/spring-data-jpa-query
     * 
     * Query explained:
     * 1) SELECT * FROM weatherdatatable
     * -> We want to get all the columns from entries that meet our criteria
     * 
     * 2) WHERE + AND
     * -> Here we set our query parameters
     * 
     * 3) (temp BETWEEN :minTemp AND :maxTemp)
     * -> we want to find locations with similar temperatures, so temp must be
     * WITHIN certain parameters so that the temperature differences are not too big
     * 
     * 4) (lon NOT BETWEEN :minLon AND :maxLon) AND (lat NOT BETWEEN :minLat AND
     * :maxLat)
     * -> we want to find locations that are not right next to each other, so lon
     * and lat must be OUTSIDE certain parameters so that the locations are
     * sufficiently far away from each other
     * 
     * 5) (weather_group = :weatherGroup)
     * -> we want to find locations that are experiencing the same kind of weather,
     * so weather groups must match
     * 
     * 6) (id != :id)
     * -> we don't want to return the input location, so we check that the ids in
     * the input location and found similar location are not the same
     */

    /**
     * This is a SQL query for finding cities in the repository that have a similar weather
     *
     * @param minTemp the minimum temperature value (inclusive) for filtering records
     * @param maxTemp the maximum temperature value (inclusive) for filtering records
     * @param minLon the lower bound of the longitude range to be excluded
     * @param maxLon the upper bound of the longitude range to be excluded
     * @param minLat the lower bound of the latitude range to be excluded
     * @param maxLat the upper bound of the latitude range to be excluded
     * @param weatherGroup input city's weather condition
     * @param id input city's id
     * @return list of WeatherData objects from the query
     */
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
