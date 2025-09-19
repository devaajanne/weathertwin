package app.weathertwin;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import app.weathertwin.service.ConversionService;
import app.weathertwin.entity.WeatherData;
import org.springframework.test.context.ActiveProfiles;

/**
 * Tests for validating the temperature conversion methods in ConversionService.
 * By default, the OpenWeatherMap API returns temperatures in standard units (Kelvin). These tests verify
 * that the conversion methods correctly convert temperatures to metric (Celsius) and imperial (Fahrenheit)
 * units. The tests run under the "test" profile.
 */
@SpringBootTest
@ActiveProfiles("test")
public class ConversionTests {

    /**
     * Tests the conversion from standard (Kelvin) to metric (Celsius) temperature units.
     */
    @Test
    public void testStandardToMetricConversion() {
        Double standardTemp = 274.15;
        Double metricTemp = 1.0;

        assertEquals(metricTemp, ConversionService.tempStandardToMetric(standardTemp));
    }

    /**
     * Tests the conversion from standard (Kelvin) to imperial (Fahrenheit) temperature units.
     */
    @Test
    public void testStandardToImperialConversion() {
        Double standardTemp = 255.93;
        Double imperialTemp = 1.0;

        assertEquals(imperialTemp, ConversionService.tempStandardToImperial(standardTemp));
    }

    /**
     * Tests the conversion of the temperature field in a WeatherData object from standard (Kelvin)
     * to metric (Celsius) units
     */
    @Test
    public void testWeatherDataTempConversionToMetric() {
        WeatherData weatherData = new WeatherData(1L, 25.55, 25.66, "Helsinki", "FI", "Finland", 274.15, null, "Snow", "13d");
        Double metricTemp = 1.0;

        WeatherData newWeatherData = ConversionService.convertTemp(weatherData, "metric");

        assertEquals(metricTemp, newWeatherData.getTemp());
    }

    /**
     * Tests the conversion of the temperature field in a WeatherData object from standard (Kelvin)
     * to imperial (Fahrenheit) units
     */
    @Test
    public void testWeatherDataTempConversionToImperial() {
        WeatherData weatherData = new WeatherData(1L, 25.55, 25.66, "Helsinki", "FI", "Finland", 255.93, null, "Snow", "13d");
        Double imperialTemp = 1.0;

        WeatherData newWeatherData = ConversionService.convertTemp(weatherData, "imperial");

        assertEquals(imperialTemp, newWeatherData.getTemp());
    }
}