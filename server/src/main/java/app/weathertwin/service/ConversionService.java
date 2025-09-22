package app.weathertwin.service;

import app.weathertwin.entity.WeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import java.text.DecimalFormat;
import org.springframework.stereotype.Service;

/**
 * This is a service class that holds methods for converting temperature units to other units, and
 * JSON objects to WeatherData objects.
 */
@Service
public class ConversionService {

  /**
   * This is a simple method to convert kelvin temperature to Celsius temperature.
   *
   * @param standardTemp temperature in kelvin
   * @return temperature in Celsius
   */
  public static Double tempStandardToMetric(Double standardTemp) {
    DecimalFormat oneDecimalPlace = new DecimalFormat("0.0");
    Double metricTemp =
        Double.valueOf(oneDecimalPlace.format(standardTemp - 273.15).replace(",", "."));
    return metricTemp;
  }

  /**
   * This is a simple method to convert kelvin temperature to Fahrenheit temperature.
   *
   * @param standardTemp temperature in kelvin
   * @return temperature in Fahrenheit
   */
  public static Double tempStandardToImperial(Double standardTemp) {
    DecimalFormat zeroDecimalPlace = new DecimalFormat("0");
    Double imperialTemp =
        Double.valueOf(zeroDecimalPlace.format(standardTemp * 1.8 - 459.67).replace(",", "."));
    return imperialTemp;
  }

  /**
   * This method converts fetched JSON weather data from the API into a WeatherData object because
   * the API returns more data than we actually need for our application. So we convert it because
   * we want to have only the data we need in our database.
   *
   * @param cityWeatherDataJSON JSON object holding all the weather data for a city
   * @return a WeatherData object with only the fields we need from the JSON object, or an empty
   *     WeatherData object
   */
  public static WeatherData JsonNodeToWeatherData(JsonNode cityWeatherDataJSON) {
    WeatherData weatherData = new WeatherData();

    if (cityWeatherDataJSON == null) {
      return weatherData;
    }

    /*
     * We find the data we need in the JSON weather data and set a WeatherData
     * object with corresponding attributes.
     * Country name and temp unit are not provided by the API, and we set them to
     * null
     * for now.
     * The country name and temp unit will be set later only if this particular
     * WeatherData object is selected to be returned to the client.
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
    weatherData.setWeatherIcon(cityWeatherDataJSON.get("weather").get(0).get("icon").asText());

    return weatherData;
  }

  /**
   * By default, we save WeatherData objects with standard (kelvin) temperatures into the database.
   * The temperature will be converted according to what unit the user selected in the client when a
   * WeatherData object is sent to the client. We also set the unit's symbol (°C or °F) according to
   * the unit.
   *
   * @param weatherData the WeatherData object where we want to convert temperature units
   * @param unit the temperature unit we want to convert the temperature to
   * @return the WeatherData object in @param with the correct temperature unit and temperature
   *     symbol
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
