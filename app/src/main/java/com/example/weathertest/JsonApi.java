package com.example.weathertest;
import com.example.weathertest.Module.City;
import com.example.weathertest.Module.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonApi {
    @GET("data/2.5/weather?")
    Call<City> getCity(@Query("q") String city, @Query("appid") String appid);
    @GET("data/2.5/forecast?")
    Call<Forecast> getforecast(@Query("q") String city, @Query("appid") String appid);
}
