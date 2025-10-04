package app.weathertwin.controller;

import app.weathertwin.entity.WeatherData;
import app.weathertwin.service.ControllerService;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** This is the REST API controller for the application */
@RestController
@RequestMapping("/api")
public class WeathertwinController {

  private final ControllerService controllerService;

  public WeathertwinController(ControllerService controllerService) {
    this.controllerService = controllerService;
  }

  /**
   * REST API endpoint that handles receiving user's input from the client and passing it to service
   * methods for processing
   *
   * @param requestParam input city as string sent from client
   * @param requestParam input latitude coordinate as double sent from client
   * @param requestParam input longitude coordinate as double sent from client
   * @param requestParam input unit as string sent from client
   * @return OK response with a two entry HashMap: input location WeatherData and similar location
   *     WeatherData
   */
  @GetMapping("/weatherdata")
  public ResponseEntity<HashMap<String, WeatherData>> getCityWeatherData(
      @RequestParam String city,
      @RequestParam double lat,
      @RequestParam double lon,
      @RequestParam String unit) {
    HashMap<String, WeatherData> responseMap =
        controllerService.getWeatherTwinData(city, lat, lon, unit);
    return ResponseEntity.ok(responseMap);
  }
}
