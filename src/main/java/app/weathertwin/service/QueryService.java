package app.weathertwin.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.weathertwin.entity.WeatherData;
import app.weathertwin.repository.WeatherDataRepository;

@Service
public class QueryService {

    private final WeatherDataRepository weatherDataRepository;

    public QueryService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    public List<WeatherData> findSimilarWeatherDataFromrepository(WeatherData inputWeatherData) {
        Double minTemp = inputWeatherData.getTemp() - 0.5;
        Double maxTemp = inputWeatherData.getTemp() + 0.5;
        Double minLon = inputWeatherData.getLon() - 10.0;
        Double maxLon = inputWeatherData.getLon() + 10.0;
        Double minLat = inputWeatherData.getLat() - 10.0;
        Double maxLat = inputWeatherData.getLat() + 10.0;
        String weatherGroup = inputWeatherData.getWeatherGroup();
        Long id = inputWeatherData.getId();

        return weatherDataRepository.findWeatherDataThatMeetsConditions(
                minTemp,
                maxTemp,
                minLon,
                maxLon,
                minLat,
                maxLat,
                weatherGroup,
                id);
    }

    public String findCountryNameByCountryCode(String countryCode) {
        String countryName = countryCode;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File countryCodesAndNamesFile = new File("src\\main\\resources\\ISO3166_CountryCodesAndNames.json");
            JsonNode rootNode = objectMapper.readTree(countryCodesAndNamesFile);
            countryName = rootNode.get(countryCode).asText();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return countryName;
    }
}
