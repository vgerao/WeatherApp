package com.test.weather.model.data;

import java.util.List;

public class FiveDaysWeatherData {

    private String dt_txt;

    private MainPageData main;

    private List<WeatherResultsData> conditions;

    public FiveDaysWeatherData(String dateText, MainPageData main, List<WeatherResultsData> conditions) {
        this.dt_txt = dateText;
        this.main = main;
        this.conditions = conditions;
    }

    public String getDateText(){
        return dt_txt;
    }

    public MainPageData getMain() {
        return main;
    }

    public List<WeatherResultsData> getConditions() {
        return conditions;
    }
}
