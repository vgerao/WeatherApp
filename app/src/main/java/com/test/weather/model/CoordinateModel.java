package com.test.weather.model;


import com.google.gson.annotations.SerializedName;

public class CoordinateModel {

    @SerializedName("lon")
    private String lon;

    @SerializedName("lat")
    private String lat;

    public CoordinateModel(String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }
}
