package com.example.weathertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weathertest.Module.WeatherThreeHours;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.myViewHodler> {

    private ArrayList<WeatherThreeHours> WeatherThreeHoursList;


    public ForecastAdapter( ArrayList<WeatherThreeHours> weatherThreeHoursList) {

        this.WeatherThreeHoursList = weatherThreeHoursList;
    }

    @NonNull
    @Override
    public myViewHodler onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_3hours, viewGroup, false);
        return new myViewHodler(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myViewHodler holder, int position) {
        WeatherThreeHours data = WeatherThreeHoursList.get(position);
        holder.forecastTime.setText(data.getTime());
        holder.forecastTemp.setText(data.getTemperatrue());
       Glide.with(holder.forecastIcon.getContext()).load(data.getIconurl()).into(holder.forecastIcon);
    }



    @Override
    public int getItemCount() {
        return WeatherThreeHoursList!=null? WeatherThreeHoursList.size():0;
    }


    class myViewHodler extends RecyclerView.ViewHolder {
        private ImageView forecastIcon;
        private TextView forecastTime;
        private TextView forecastTemp;

        public myViewHodler(View itemView) {
            super(itemView);
            forecastIcon =  itemView.findViewById(R.id.weather_icon_recyclerView);
            forecastTime = itemView.findViewById(R.id.hour_forecast_recyclerView);
            forecastTemp =  itemView.findViewById(R.id.temp_forecast_recyclerView);
        }
    }





}
