package com.example.weathertest.Module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class City {
    @SerializedName("coord")
    private Coord coord;


    public List<Weather> getWeatherlist() {
        return weatherlist;
    }

    @SerializedName("weather")
    private List<Weather> weatherlist;
    private String base;
    private Main main;
    private float visibility;
    @SerializedName("wind")
    private Wind wind;
    private Cloud cloud;
    private long dt;
    @SerializedName("sys")
    private Sys sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;

    public Coord getCoord() {
        return coord;
    }


    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public float getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public long getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }
}
