package com.test.weather.view;

import com.test.weather.model.WeatherObject;

import java.util.List;

public interface UpdateUIHandler {
    public void updatePresentDayDetails(Object object);
    public void updateNextFiveDaysDetails(List<WeatherObject> daysOfTheWeek);
    public void updateErrorDetails();
}