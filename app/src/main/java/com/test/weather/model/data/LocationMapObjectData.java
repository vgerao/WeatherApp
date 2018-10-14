package com.test.weather.model.data;


import java.util.List;

public class LocationMapObjectData {

    private CoordinateData coord;

    private List<WeatherResultsData> weather;

    private String base;

    private MainPageData main;

    private String visibility;

    private WindData wind;

    private RainData rain;

    private CloudData clouds;

    private String dt;

    private SysData sys;

    private String id;

    private String name;

    private String cod;

    public LocationMapObjectData(CoordinateData coord, List<WeatherResultsData> weather, String base, MainPageData main, String visibility, WindData wind, RainData rain, CloudData clouds, String dt, SysData sys, String id, String name, String cod) {
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.rain = rain;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public CoordinateData getCoord() {
        return coord;
    }

    public List<WeatherResultsData> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public MainPageData getMain() {
        return main;
    }

    public String getVisibility() {
        return visibility;
    }

    public WindData getWind() {
        return wind;
    }

    public RainData getRain() {
        return rain;
    }

    public CloudData getClouds() {
        return clouds;
    }

    public String getDt() {
        return dt;
    }

    public SysData getSys() {
        return sys;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCod() {
        return cod;
    }
}
