package com.test.weather.model;

import com.google.gson.annotations.SerializedName;

public class ListJsonObject {

    @SerializedName("_id")
    private String _id;

    @SerializedName("name")
    private String name;

    @SerializedName("country")
    private String country;

    @SerializedName("coord")
    private CoordinateModel coord;

    public ListJsonObject(String _id, String name, String country, CoordinateModel coord) {
        this._id = _id;
        this.name = name;
        this.country = country;
        this.coord = coord;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public CoordinateModel getCoord() {
        return coord;
    }
}
