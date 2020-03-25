package com.example.weathertest.ui.Precipitation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weathertest.ForecastAdapter;
import com.example.weathertest.JsonApi;
import com.example.weathertest.Module.City;
import com.example.weathertest.Module.Forecast;
import com.example.weathertest.Module.WeatherThreeHours;
import com.example.weathertest.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrecipitationFragment extends Fragment {
    private RecyclerView RecyclerForecast;
    private  TextView title_tv;
    private ImageView icon_weather;
    private TextView temperature;
    private TextView icon_text;
    private TextView humidity_tv;
    private TextView speedwind_tv;
    private ArrayList<WeatherThreeHours> forecastlist;
    private ForecastAdapter mCollectRecyclerAdapter;
    private  Call<Forecast> call;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_precipitation,container,false);


        return  view;
    }
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        temperature = view.findViewById(R.id.temp_city);
        icon_weather = view.findViewById(R.id.weather_icon_city);
        title_tv = view.findViewById(R.id.text_city);
        humidity_tv=view.findViewById(R.id.humidity_city);
        speedwind_tv=view.findViewById(R.id.speedwind_city);
        RecyclerForecast = view.findViewById(R.id.weather_by_hours_recyclerView);
        RecyclerForecast.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        forecastlist=new ArrayList<WeatherThreeHours>();
        title_tv.setText("city fragment");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        call = jsonApi.getforecast("courbevoie", "ea8337c9cb0fbd622fc3b87139ecddce");




        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (!response.isSuccessful()) {
                    title_tv.setText("Code :" + response.code());
                    return;
                }

                   Forecast forecast = response.body();

                    title_tv.setText(forecast.getCity().getName());

                    temperature.setText(String.format("%.2f",(forecast.getListinfo().get(0).getMain().getTemp()-273.1)) + "℃");
                    humidity_tv.setText("Humidity : "+forecast.getListinfo().get(0).getMain().getHumidity());
                    speedwind_tv.setText("Wind Speed:" + forecast.getListinfo().get(0).getWind().getSpeed()+"M/S");
                    String icon = forecast.getListinfo().get(0).getWeatherList().get(0).getIcon();
                    String imageUrl = "https://openweathermap.org/img/wn/" + icon + "@2x.png";
                    Glide.with(icon_weather.getContext()).load(imageUrl).into(icon_weather);
                try {
                    for(int i =1;i<=5;i++){
                        icon = forecast.getListinfo().get(i).getWeatherList().get(0).getIcon();
                        imageUrl = "https://openweathermap.org/img/wn/" + icon + "@2x.png";
                        String time = forecast.getListinfo().get(i).getDt_txt().split(" ")[1];
                        WeatherThreeHours weatherThreeHours=new WeatherThreeHours(imageUrl,String.format("%.2f",(forecast.getListinfo().get(i).getMain().getTemp()-273.1)) + "℃",time.split(":")[0]+"h");
                        forecastlist.add(weatherThreeHours);
                    }
                    ForecastAdapter forecastAdapter= new ForecastAdapter(forecastlist);
                    RecyclerForecast.setAdapter(forecastAdapter);
                }catch (Exception e){
                    title_tv.setText(e.toString());
                }
            }
            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                title_tv.setText(t.getMessage());
            }
        });

    }




}