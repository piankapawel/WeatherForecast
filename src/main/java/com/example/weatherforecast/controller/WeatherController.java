package com.example.weatherforecast.controller;

import com.example.weatherforecast.model.Forecast;
import com.example.weatherforecast.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/weather")
@Tag(name = "Weather Forecast", description = "REST API for Weather Forecast")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/forecast")
    @Operation(summary = "Get 3-day weather forecast for 5 cities in Poland")
    public List<Forecast> getWeatherForecast() {
        List<String> cities = Arrays.asList("Warsaw", "Lodz", "Krakow", "Wroclaw", "Poznan");
        return weatherService.getWeatherForecastForCities(cities);
    }
}