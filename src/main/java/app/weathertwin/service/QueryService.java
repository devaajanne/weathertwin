package app.weathertwin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.weathertwin.entity.WeatherData;
import app.weathertwin.repository.WeatherDataRepository;

@Service
public class QueryService {

    private final WeatherDataRepository weatherDataRepository;

    public QueryService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    public List<WeatherData> findSimilarWeatherDataFromrepository(WeatherData inputWeatherData) {
        Double minTemp = inputWeatherData.getTemp() - 1.0;
        Double maxTemp = inputWeatherData.getTemp() + 1.0;
        Long id = inputWeatherData.getId();

        return weatherDataRepository.findWeatherDataThatMeetsConditions(minTemp, maxTemp, id);
    }
}
