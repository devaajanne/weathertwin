package app.weathertwin.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.weathertwin.service.ConversionService;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * Tests for validating the temperature conversion methods in ConversionService. By default, the
 * OpenWeatherMap API returns temperatures in standard units (Kelvin). These tests verify that the
 * conversion methods correctly convert temperatures to metric (Celsius) and imperial (Fahrenheit)
 * units. The tests run under the "test" profile.
 */
@ActiveProfiles("test")
public class ConversionServiceUnitTests {

  /** Tests the conversion from standard (Kelvin) to metric (Celsius) temperature units. */
  @Test
  public void testStandardToMetricConversion() {
    Double standardTemp = 274.15;
    Double metricTemp = 1.0;

    assertEquals(metricTemp, ConversionService.tempStandardToMetric(standardTemp));
  }

  /** Tests the conversion from standard (Kelvin) to imperial (Fahrenheit) temperature units. */
  @Test
  public void testStandardToImperialConversion() {
    Double standardTemp = 255.93;
    Double imperialTemp = 1.0;

    assertEquals(imperialTemp, ConversionService.tempStandardToImperial(standardTemp));
  }
}
