package com.example.weatherforecast;

import com.example.weatherforecast.controller.WeatherController;
import com.example.weatherforecast.model.Forecast;
import com.example.weatherforecast.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    public WeatherControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }

    @Test
    void testGetWeatherForecast() throws Exception {
        Forecast forecast = new Forecast();
        forecast.setCity("Warsaw");
        forecast.setDate("2024-06-11");
        forecast.setMaxTempC(25.0);
        forecast.setMinTempC(15.0);
        forecast.setAvgTempC(20.0);
        forecast.setMaxWindKph(10.0);
        forecast.setTotalPrecipMm(5.0);
        forecast.setTotalSnowCm(0.0);
        forecast.setAvgHumidity(60.0);
        forecast.setAvgVisibilityKm(10.0);
        forecast.setUvIndex(5.0);

        List<Forecast> forecasts = Arrays.asList(forecast);

        when(weatherService.getWeatherForecastForCities(Arrays.asList("Warsaw", "Krakow", "Lodz", "Wroclaw", "Poznan")))
                .thenReturn(forecasts);

        mockMvc.perform(get("/api/weather/forecast"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Warsaw"))
                .andExpect(jsonPath("$[0].date").value("2024-06-11"))
                .andExpect(jsonPath("$[0].maxTempC").value(25.0))
                .andExpect(jsonPath("$[0].minTempC").value(15.0))
                .andExpect(jsonPath("$[0].avgTempC").value(20.0))
                .andExpect(jsonPath("$[0].maxWindKph").value(10.0))
                .andExpect(jsonPath("$[0].totalPrecipMm").value(5.0))
                .andExpect(jsonPath("$[0].totalSnowCm").value(0.0))
                .andExpect(jsonPath("$[0].avgHumidity").value(60.0))
                .andExpect(jsonPath("$[0].avgVisibilityKm").value(10.0))
                .andExpect(jsonPath("$[0].uvIndex").value(5.0));
    }
}