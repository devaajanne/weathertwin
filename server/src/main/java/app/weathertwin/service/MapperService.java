package app.weathertwin.service;

import app.weathertwin.domain.WeatherData;
import app.weathertwin.dto.WeatherDataResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MapperService {

  /* Mapper from Entity to Response */
  public WeatherDataResponse toWeatherDataResponse(WeatherData weatherData) {
    return new WeatherDataResponse(
        weatherData.getLat(),
        weatherData.getLon(),
        weatherData.getCity(),
        weatherData.getCountryCode(),
        weatherData.getCountryName(),
        weatherData.getTemp(),
        weatherData.getTempUnit(),
        weatherData.getWeatherGroup(),
        weatherData.getWeatherId());
  }

  /* Mapper from a list of Entities to a list of Responses */
  public List<WeatherDataResponse> toWeatherDataResponseList(List<WeatherData> weatherDataList) {
    return weatherDataList.stream().map(this::toWeatherDataResponse).toList();
  }
}
