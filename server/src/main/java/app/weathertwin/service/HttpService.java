package app.weathertwin.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * This is a service class to hold a method for making an API call to fetch weather data as a JSON
 * object from the API.
 */
@Service
public class HttpService {

  /*
   * API key is referenced from application properties file. This is not included
   * in version control for safety reasons. Source:
   * https://www.baeldung.com/spring-inject-static-field
   */
  private static String OPEN_WEATHER_API_KEY;

  @Value("${OpenWeatherAPIkey}")
  public void setStaticName(String name) {
    OPEN_WEATHER_API_KEY = name;
  }

  private static final HttpClient client = HttpClient.newHttpClient();

  /**
   * This method fetches weather data from OpenWeatherMap. Current weather data API call uses lon
   * and lat data. We got this data either through a library in the client, and passed to the server
   * through http body, or through citiesLatsAndLons.json file.
   *
   * @param lat latitude coordinate of the city.
   * @param lon longitude coordinate of the city.
   * @return JSON object with the city's weather data.
   */
  public JsonNode fetchWeatherData(Double lat, Double lon) {
    JsonNode weatherData = JsonNodeFactory.instance.objectNode();

    /*
     * Here we construct the URL for the API call using lat and lon data and our private API key
     */
    final String WEATHERDATA_URL =
        "https://api.openweathermap.org/data/2.5/weather?lat="
            + lat
            + "&lon="
            + lon
            + "&appid="
            + OPEN_WEATHER_API_KEY;

    /*
     * Here we create a new http request using URL and http request method
     * Sources: https://www.baeldung.com/java-9-http-client,
     * https://www.baeldung.com/java-uri-create-and-new-uri
     */
    HttpRequest httpRequest =
        HttpRequest.newBuilder().uri(URI.create(WEATHERDATA_URL)).GET().build();

    /*
     * Here we try to actually fetch the weather data from the API. If we run into
     * an exception, stack trace is printed
     */
    try {
      CompletableFuture<HttpResponse<String>> futureResponse =
          client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

      HttpResponse<String> response = futureResponse.join();

      /* Source: https://www.baeldung.com/jackson-object-mapper-tutorial */
      ObjectMapper objectMapper = new ObjectMapper();
      weatherData = objectMapper.readTree(response.body());

    } catch (IOException exception) {
      exception.printStackTrace();
    }

    return weatherData;
  }
}
