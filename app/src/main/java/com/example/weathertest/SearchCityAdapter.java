package com.example.weathertest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weathertest.Module.City;
import com.example.weathertest.Module.WeatherThreeHours;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchCityAdapter extends RecyclerView.Adapter<SearchCityAdapter.myViewHodler> {
    private ArrayList<String> SearchCityList;


    public SearchCityAdapter( ArrayList<String> searchCityList) {

        this.SearchCityList= searchCityList;
    }

    @NonNull
    @Override
    public SearchCityAdapter.myViewHodler onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.city_search, viewGroup, false);
        return new SearchCityAdapter.myViewHodler(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SearchCityAdapter.myViewHodler holder, int position) {
        String data = SearchCityList.get(position);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
       Call<City> call = jsonApi.getCity(data, "ea8337c9cb0fbd622fc3b87139ecddce");
        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                if (!response.isSuccessful()) {
                    holder.cityName.setText("Code :" + response.code());
                    return;
                }

                try {
                    City city = response.body();
                    holder.cityName.setText(city.getName());
                    holder.cityTemp.setText(String.format("%.2f",(city.getMain().getTemp()-273.1)) + "℃");

                    holder.citywindspeed.setText("Wind Speed:" + city.getWind().getSpeed()+"M/S");
                    String icon = city.getWeatherlist().get(0).getIcon();
                    String imageUrl = "https://openweathermap.org/img/wn/" + icon + "@2x.png";
                    Glide.with(holder.cityIcon.getContext()).load(imageUrl).into(holder.cityIcon);



                }catch (Exception e){
                    holder.cityName.setText(e.toString());
                }
            }
            @Override
            public void onFailure(Call<City> call, Throwable t) {
                holder.cityName.setText(t.getMessage());
            }
        });
    }



    @Override
    public int getItemCount() {
        return SearchCityList!=null? SearchCityList.size():0;
    }


    class myViewHodler extends RecyclerView.ViewHolder {
        public ImageView cityIcon;
        public TextView cityName;
        public TextView cityTemp;
        public TextView citywindspeed;

        public myViewHodler(View itemView) {
            super(itemView);
            cityIcon =  itemView.findViewById(R.id.icon_recyclerCity);
            cityName= itemView.findViewById(R.id.name_recyclerCity);
            cityTemp =  itemView.findViewById(R.id.temp_recyclerCity);
            citywindspeed=itemView.findViewById(R.id.wind_speed_recyclerCity);
        }
    }



}
