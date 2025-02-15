package app.weathertwin.service;

import java.text.DecimalFormat;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import app.weathertwin.entity.WeatherData;

@Service
public class ConversionService {

    /*
     * This is a simple method to convert kelvin temperature to celsius temperature
     */
    public static Double tempStandardToMetric(Double standardTemp) {
        DecimalFormat oneDecimalPlace = new DecimalFormat("0.0");
        Double metricTemp = Double.valueOf(oneDecimalPlace.format(standardTemp - 273.15).replace(",", "."));
        return metricTemp;
    }

    /*
     * This is a simple method to convert kelvin temperature to fahrenheit
     * temperature
     */
    public static Double tempStandardToImperial(Double standardTemp) {
        DecimalFormat zeroDecimalPlace = new DecimalFormat("0");
        Double imperialTemp = Double.valueOf(zeroDecimalPlace.format(standardTemp * 1.8 - 459.67).replace(",", "."));
        return imperialTemp;
    }

    /*
     * This method converts fetched JSON weather data from the API into a
     * WeatherData object because the API returns more data than we actually need
     * for our application. So we convert it because we want to have only the data
     * we need in our database
     */
    public static WeatherData JsonNodeToWeatherData(JsonNode cityWeatherDataJSON) {
        WeatherData weatherData = new WeatherData();

        if (cityWeatherDataJSON == null) {
            return weatherData;
        }

        /*
         * We find the data we need in the JSON weather data and set a WeatherData
         * object with corresponding attributes. Country name is not provided by the
         * API, and we set it to null for now. The country name will be se only if this
         * particular WeatherData object is selected to be returned to the client. Also,
         * temp unit will be set later if the location is returned to the client.
         */
        weatherData.setId(cityWeatherDataJSON.get("id").asLong());
        weatherData.setLat(cityWeatherDataJSON.get("coord").get("lat").asDouble());
        weatherData.setLon(cityWeatherDataJSON.get("coord").get("lon").asDouble());
        weatherData.setCity(cityWeatherDataJSON.get("name").asText());
        weatherData.setCountryCode(cityWeatherDataJSON.get("sys").get("country").asText());
        weatherData.setCountryName(null);
        weatherData.setTemp(cityWeatherDataJSON.get("main").get("temp").asDouble());
        weatherData.setTempUnit(null);
        weatherData.setWeatherGroup(cityWeatherDataJSON.get("weather").get(0).get("main").asText());

        return weatherData;
    }

    /*
     * By default, we save WeatherData objects with standard (kelvin) temperatures
     * into the database. The temperature will be converted according to what unit
     * the user selected in the client when a WeatherData object is sent to the
     * client. We also set the unit's symbol (°C or °F) according to the unit.
     */
    public static WeatherData convertTemp(WeatherData weatherData, String unit) {

        /* If no unit is given, standard (kelvin) temperature is returned */
        if (unit == null) {
            weatherData.setTemp(weatherData.getTemp());
        }

        if (unit != null && unit.equals("metric")) {
            weatherData.setTemp(tempStandardToMetric(weatherData.getTemp()));
            weatherData.setTempUnit("°C");
        }

        if (unit != null && unit.equals("imperial")) {
            weatherData.setTemp(tempStandardToImperial(weatherData.getTemp()));
            weatherData.setTempUnit("°F");
        }

        return weatherData;
    }
}
