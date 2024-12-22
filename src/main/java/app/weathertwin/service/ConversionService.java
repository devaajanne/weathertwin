package app.weathertwin.service;

import java.text.DecimalFormat;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import app.weathertwin.entity.WeatherData;

@Service
public class ConversionService {

    public static Double tempStandardToMetric(Double standardTemp) {
        DecimalFormat oneDecimalPlace = new DecimalFormat("0.0");
        Double metricTemp = Double.valueOf(oneDecimalPlace.format(standardTemp - 273.15).replace(",", "."));
        return metricTemp;
    }

    public static Double tempStandardToImperial(Double standardTemp) {
        DecimalFormat zeroDecimalPlace = new DecimalFormat("0");
        Double imperialTemp = Double.valueOf(zeroDecimalPlace.format(standardTemp * 1.8 - 459.67).replace(",", "."));
        return imperialTemp;
    }

    public static WeatherData JsonNodeToWeatherData(JsonNode cityWeatherDataJSON, String unit) {
        WeatherData weatherData = new WeatherData();

        if (cityWeatherDataJSON == null) {
            return weatherData;
        } else {
            weatherData.setId(cityWeatherDataJSON.get("id").asInt());
            weatherData.setLat(cityWeatherDataJSON.get("coord").get("lat").asDouble());
            weatherData.setLon(cityWeatherDataJSON.get("coord").get("lon").asDouble());
            weatherData.setCountryCode(cityWeatherDataJSON.get("sys").get("country").asText());
            weatherData.setCity(cityWeatherDataJSON.get("name").asText());

            if (unit == null) {
                weatherData.setTemp(cityWeatherDataJSON.get("main").get("temp").asDouble());
            }

            if (unit != null && unit.equals("metric")) {
                weatherData.setTemp(tempStandardToMetric(cityWeatherDataJSON.get("main").get("temp").asDouble()));
            }

            if (unit != null && unit.equals("imperial")) {
                weatherData.setTemp(tempStandardToImperial(cityWeatherDataJSON.get("main").get("temp").asDouble()));
            }
        }

        return weatherData;
    }
}
