package com.example.weathertest.Module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {
    private String cod;
    private int message;
    private int cnt;

    public List<Forcast_info> getListinfo() {
        return listinfo;
    }

    @SerializedName("list")
    private List<Forcast_info> listinfo;

    public String getCod() {
        return cod;
    }

    public int getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }



    public Forecastcity getCity() {
        return city;
    }

    @SerializedName("city")
    private Forecastcity city;
}
