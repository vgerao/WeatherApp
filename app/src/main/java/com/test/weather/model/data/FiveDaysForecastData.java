package com.test.weather.model.data;

public class FiveDaysForecastData {

    private ForecastData dailyForecastData;

    public FiveDaysForecastData(ForecastData dailyForecast) {
        this.dailyForecastData = dailyForecast;
    }

    public ForecastData getDailyForecastData() {
        return dailyForecastData;
    }
}
