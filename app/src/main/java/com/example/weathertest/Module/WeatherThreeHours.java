package com.example.weathertest.Module;

public class WeatherThreeHours {
    private String iconurl;
    private String temperatrue;
    private String time;

    public WeatherThreeHours(String iconurl, String temperatrue, String time) {
        this.iconurl = iconurl;
        this.temperatrue = temperatrue;
        this.time = time;
    }

    public WeatherThreeHours() {
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getTemperatrue() {
        return temperatrue;
    }

    public void setTemperatrue(String temperatrue) {
        this.temperatrue = temperatrue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
