package com.example.weathertest.Module;

public class Coord {
    private float lon;

    public float getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public Coord(float lon, float lat) {
        this.lon = lon;
        this.lat = lat;
    }

    private float lat;
}