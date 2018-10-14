package com.test.weather.model.data;

public class CoordinateData {

    private String longitude;

    private String latitude;

    public CoordinateData(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }
}
