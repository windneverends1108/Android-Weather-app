package com.example.weathertest.Module;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forcast_info {
       @SerializedName("dt")
        private long dt;
       private Main main;

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    @SerializedName("weather")
        private List<Weather> weatherList;
       private Cloud cloud;
       private Wind wind;

    public long getDt() {
        return dt;
    }

    public Main getMain() {
        return main;
    }



    public Cloud getCloud() {
        return cloud;
    }

    public Wind getWind() {
        return wind;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    private String  dt_txt;
}
