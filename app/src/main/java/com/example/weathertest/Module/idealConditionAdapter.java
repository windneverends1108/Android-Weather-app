package com.example.weathertest.Module;


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
import com.example.weathertest.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class idealConditionAdapter extends RecyclerView.Adapter<idealConditionAdapter.myViewHodler> {

    private ArrayList<String> windConditionList;

    public idealConditionAdapter( ArrayList<String> windConditionList) {

        this.windConditionList= windConditionList;
    }


    @NonNull
    @Override
    public idealConditionAdapter.myViewHodler onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ideal_condition_recycler_view, viewGroup, false);
        return new idealConditionAdapter.myViewHodler(view);
    }


    @Override
    public void onBindViewHolder(@NonNull idealConditionAdapter.myViewHodler holder, int position) {
        String data = windConditionList.get(position);
        holder.citywindspeed.setText("Ideal wind :" + data +"M/S");

    }


    @Override
    public int getItemCount() {
        return windConditionList!=null? windConditionList.size():0;
    }

    class myViewHodler extends RecyclerView.ViewHolder {
        public TextView citywindspeed;

        public myViewHodler(View itemView) {
            super(itemView);
            citywindspeed = itemView.findViewById(R.id.windCondition);
        }
    }
}