package com.test.weather.model.data;


public class WeatherData {

    WeatherResultsData weatherResults;

    public WeatherData(WeatherResultsData weatherResults){
        this.weatherResults = weatherResults;
    }

    public WeatherResultsData getWeatherResults() {
        return weatherResults;
    }
}
