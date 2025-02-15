package app.weathertwin;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import app.weathertwin.service.ConversionService;
import app.weathertwin.entity.WeatherData;

@SpringBootTest(properties = "spring.profiles.active=test")
public class ConversionTests {

    /*
     * By default, the OpenWeatherMap API returns us standard (kelvin) units. Here
     * we test that the temperature unit conversions work properly, ie. standard ->
     * metric (celsius) and standard -> imperial (fahrenheit)
     */

    @Test
    public void testStandardToMetricConversion() {
        Double standardTemp = 274.15;
        Double metricTemp = 1.0;

        assertEquals(metricTemp, ConversionService.tempStandardToMetric(standardTemp));
    }

    @Test
    public void testStandardToImperialConversion() {
        Double standardTemp = 255.93;
        Double imperialTemp = 1.0;

        assertEquals(imperialTemp, ConversionService.tempStandardToImperial(standardTemp));
    }

    @Test
    public void testWeatherDataTempConversionToMetric() {
        WeatherData weatherData = new WeatherData(1L, 25.55, 25.66, "Helsinki", "FI", "Finland", 274.15, null, "Snow", "13d");
        Double metricTemp = 1.0;

        WeatherData newWeatherData = ConversionService.convertTemp(weatherData, "metric");

        assertEquals(metricTemp, newWeatherData.getTemp());
    }

    @Test
    public void testWeatherDataTempConversionToImperial() {
        WeatherData weatherData = new WeatherData(1L, 25.55, 25.66, "Helsinki", "FI", "Finland", 255.93, null, "Snow", "13d");
        Double imperialTemp = 1.0;

        WeatherData newWeatherData = ConversionService.convertTemp(weatherData, "imperial");

        assertEquals(imperialTemp, newWeatherData.getTemp());
    }
}