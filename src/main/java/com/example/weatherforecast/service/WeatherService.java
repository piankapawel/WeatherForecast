package com.example.weatherforecast.service;

import com.example.weatherforecast.model.Forecast;
import com.example.weatherforecast.service.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    @Value("${weatherapi.base-url}")
    private String baseUrl;

    @Value("${weatherapi.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Forecast> getWeatherForecastForCities(List<String> cities) {
        List<Forecast> forecasts = new ArrayList<>();
        for (String city : cities) {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/forecast.json")
                    .queryParam("key", apiKey)
                    .queryParam("q", city)
                    .queryParam("days", 3)
                    .toUriString();

            WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);
            assert response != null;
            forecasts.addAll(mapToForecast(response, city));
        }
        return forecasts;
    }

    private List<Forecast> mapToForecast(WeatherApiResponse response, String city) {
        List<Forecast> forecasts = new ArrayList<>();
        for (WeatherApiResponse.Forecastday day : response.getForecast().getForecastday()) {
            Forecast forecast = new Forecast();
            forecast.setCity(city);
            forecast.setDate(day.getDate());
            forecast.setMaxTempC(day.getDay().getMaxtempC());
            forecast.setMinTempC(day.getDay().getMintempC());
            forecast.setAvgTempC(day.getDay().getAvgtempC());
            forecast.setMaxWindKph(day.getDay().getMaxwindKph());
            forecast.setTotalPrecipMm(day.getDay().getTotalprecipMm());
            forecast.setTotalSnowCm(day.getDay().getTotalsnowCm());
            forecast.setAvgHumidity(day.getDay().getAvghumidity());
            forecast.setAvgVisibilityKm(day.getDay().getAvgvisKm());
            forecast.setUvIndex(day.getDay().getUv());
            forecasts.add(forecast);
        }
        return forecasts;
    }
}