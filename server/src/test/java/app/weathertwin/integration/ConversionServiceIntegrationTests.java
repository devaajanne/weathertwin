package app.weathertwin.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.weathertwin.entity.WeatherData;
import app.weathertwin.repository.WeatherDataRepository;
import app.weathertwin.service.ConversionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Tests for validating the temperature conversion methods in ConversionService. By default, the
 * OpenWeatherMap API returns temperatures in standard units (Kelvin). These tests verify that the
 * conversion methods correctly convert temperatures to metric (Celsius) and imperial (Fahrenheit)
 * units. The tests run under the "test" profile.
 */
@SpringBootTest
@ActiveProfiles("test")
public class ConversionServiceIntegrationTests {

  @Autowired private WeatherDataRepository weatherDataRepository;

  /**
   * Tests the conversion of the temperature field in a WeatherData object from standard (Kelvin) to
   * metric (Celsius) units
   */
  @Test
  public void testWeatherDataTempConversionToMetric() {
    WeatherData weatherData = weatherDataRepository.findById(1L).get();
    Double metricTemp = 2.0;

    WeatherData newWeatherData = ConversionService.convertTemp(weatherData, "metric");

    assertEquals(metricTemp, newWeatherData.getTemp());
  }

  /**
   * Tests the conversion of the temperature field in a WeatherData object from standard (Kelvin) to
   * imperial (Fahrenheit) units
   */
  @Test
  public void testWeatherDataTempConversionToImperial() {
    WeatherData weatherData = weatherDataRepository.findById(1L).get();
    Double imperialTemp = 36.0;

    WeatherData newWeatherData = ConversionService.convertTemp(weatherData, "imperial");

    assertEquals(imperialTemp, newWeatherData.getTemp());
  }
}
