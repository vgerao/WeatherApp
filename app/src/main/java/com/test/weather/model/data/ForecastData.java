package com.test.weather.model.data;


import java.util.List;

public class ForecastData {

    private List<FiveDaysWeatherData> list;

    public ForecastData(List<FiveDaysWeatherData> list) {
        this.list = list;
    }

    public List<FiveDaysWeatherData> getList() {
        return list;
    }
}
