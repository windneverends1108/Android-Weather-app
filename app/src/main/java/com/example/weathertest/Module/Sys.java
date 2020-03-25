package com.example.weathertest.Module;

import com.google.gson.annotations.SerializedName;

public class Sys {
    private int type;
    private int id;
    private String country;
    @SerializedName("sunrise")
    private long sunrise;

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }
    @SerializedName("sunset")
    private long sunset;

    public Sys(int type, int id, String country, long sunrise, long sunset) {
        this.type = type;
        this.id = id;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
}
