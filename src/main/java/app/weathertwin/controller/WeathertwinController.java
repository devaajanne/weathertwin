package app.weathertwin.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.weathertwin.service.HttpService;

@RestController
@RequestMapping("api/weather")
public class WeathertwinController {

    private HttpService httpService;

    public WeathertwinController(HttpService httpService) {
        this.httpService = httpService;
    }

    @GetMapping("/{city}")
    public HashMap<String, Double> findLatAndLon(@PathVariable String city) {
        HashMap<String, Double> latAndLonMap = new HashMap<String, Double>();

        latAndLonMap = httpService.fetchLatAndLon(city);

        return latAndLonMap;
    }

}
