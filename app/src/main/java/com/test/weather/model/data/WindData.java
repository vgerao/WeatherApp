package com.test.weather.model.data;

public class WindData {

    private String speed;

    private String deg;

    public WindData(String speed, String deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public String getSpeed() {
        return speed;
    }

    public String getDeg() {
        return deg;
    }
}
