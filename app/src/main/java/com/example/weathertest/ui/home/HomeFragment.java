package com.example.weathertest.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.bumptech.glide.Glide;
import com.example.weathertest.Module.City;
import com.example.weathertest.JsonApi;
import com.example.weathertest.Module.Weather;
import com.example.weathertest.R;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private  TextView home_tv;
    private ImageView icon_weather;
    private TextView temperature;
    private TextView icon_text;
    private TextView humidity_tv;
    private TextView speedwind_tv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return  view;
    }
    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        icon_text = view.findViewById(R.id.icon_text);
        temperature = view.findViewById(R.id.temp_home);
        icon_weather = view.findViewById(R.id.weather_icon);
        home_tv = view.findViewById(R.id.text_home);
        humidity_tv=view.findViewById(R.id.humidity_home);
        speedwind_tv=view.findViewById(R.id.speedwind_home);
        home_tv.setText("Home fragment");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        Call<City> call = jsonApi.getCity("courbevoie", "ea8337c9cb0fbd622fc3b87139ecddce");

        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                if (!response.isSuccessful()) {
                    home_tv.setText("Code :" + response.code());
                    return;
                }

                try {
                    City city = response.body();
                    home_tv.setText(city.getName());
                    temperature.setText(String.format("%.2f",(city.getMain().getTemp()-273.1)) + "℃");
                    humidity_tv.setText("Humidity : "+city.getMain().getHumidity());
                    speedwind_tv.setText("Wind Speed:" + city.getWind().getSpeed()+"M/S");
                    String icon = city.getWeatherlist().get(0).getIcon();
                    String imageUrl = "https://openweathermap.org/img/wn/" + icon + "@2x.png";
                    Glide.with(icon_weather.getContext()).load(imageUrl).into(icon_weather);



                }catch (Exception e){
                    home_tv.setText(e.toString());
                }
            }
            @Override
            public void onFailure(Call<City> call, Throwable t) {
                home_tv.setText(t.getMessage());
            }
        });


    }




}