package com.example.weatherforecast.service;

import com.example.weatherforecast.model.Forecast;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private RestTemplate restTemplate;

    @Value("${weatherapi.base-url}")
    private String baseUrl;

    @Value("${weatherapi.api-key}")
    private String apiKey;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWeatherForecastForCities() {
        String city = "Warsaw";
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/forecast.json")
                .queryParam("key", apiKey)
                .queryParam("q", city)
                .queryParam("days", 3)
                .toUriString();

        WeatherApiResponse mockResponse = new WeatherApiResponse();
        WeatherApiResponse.Forecast forecast = getForecast();
        mockResponse.setForecast(forecast);

        when(restTemplate.getForObject(url, WeatherApiResponse.class)).thenReturn(mockResponse);

        List<Forecast> forecasts = weatherService.getWeatherForecastForCities(Arrays.asList(city));

        assertEquals(1, forecasts.size());
        Forecast forecastResult = forecasts.get(0);
        assertEquals(city, forecastResult.getCity());
        assertEquals("2024-06-11", forecastResult.getDate());
        assertEquals(25.0, forecastResult.getMaxTempC());
        assertEquals(15.0, forecastResult.getMinTempC());
        assertEquals(20.0, forecastResult.getAvgTempC());
        assertEquals(10.0, forecastResult.getMaxWindKph());
        assertEquals(5.0, forecastResult.getTotalPrecipMm());
        assertEquals(0.0, forecastResult.getTotalSnowCm());
        assertEquals(60.0, forecastResult.getAvgHumidity());
        assertEquals(10.0, forecastResult.getAvgVisibilityKm());
        assertEquals(5.0, forecastResult.getUvIndex());
    }

    private static WeatherApiResponse.Forecast getForecast() {
        WeatherApiResponse.Forecast forecast = new WeatherApiResponse.Forecast();
        WeatherApiResponse.Forecastday forecastday = new WeatherApiResponse.Forecastday();
        WeatherApiResponse.Forecastday.Day day = new WeatherApiResponse.Forecastday.Day();

        day.setMaxtempC(25.0);
        day.setMintempC(15.0);
        day.setAvgtempC(20.0);
        day.setMaxwindKph(10.0);
        day.setTotalprecipMm(5.0);
        day.setTotalsnowCm(0.0);
        day.setAvghumidity(60.0);
        day.setAvgvisKm(10.0);
        day.setUv(5.0);

        forecastday.setDate("2024-06-11");
        forecastday.setDay(day);

        forecast.setForecastday(Arrays.asList(forecastday));
        return forecast;
    }
}
