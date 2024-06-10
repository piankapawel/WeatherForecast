package com.example.weatherforecast.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherApiResponse {
    private Forecast forecast;

    @Data
    public static class Forecast {
        private List<Forecastday> forecastday;
    }

    @Data
    public static class Forecastday {
        private String date;
        private Day day;

        @Data
        public static class Day {
            @JsonProperty("maxtemp_c")
            private double maxtempC;

            @JsonProperty("mintemp_c")
            private double mintempC;

            @JsonProperty("avgtemp_c")
            private double avgtempC;

            @JsonProperty("maxwind_kph")
            private double maxwindKph;

            @JsonProperty("totalprecip_mm")
            private double totalprecipMm;

            @JsonProperty("totalsnow_cm")
            private double totalsnowCm;

            @JsonProperty("avghumidity")
            private double avghumidity;

            @JsonProperty("avgvis_km")
            private double avgvisKm;

            private double uv;
        }
    }
}