package com.example.weathertest.ui.Temperature;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertest.R;
import com.example.weathertest.SearchCityAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TemperatureFragment extends Fragment {

    private TextView title_temp;
    private EditText search_city;
    private Button Badd;
    private Button Bclear;
    private RecyclerView RecyclerCity;
    private ArrayList<String> cityList;
    int i;
    public static final  String SHARED_PREFS="sharedPrefs";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature,container,false);
        return  view;
    }
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityList=new ArrayList<>();
        load();
        title_temp=view.findViewById(R.id.text_temperature);
        search_city=view.findViewById(R.id.search_city);
        Badd=view.findViewById(R.id.button_add_temperature);
        Bclear=view.findViewById(R.id.button_add_clear_citylist);
        RecyclerCity=view.findViewById(R.id.search_city_recyclerView);
        try{
            RecyclerCity.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            SearchCityAdapter searchCityAdapter=new SearchCityAdapter(cityList);
            RecyclerCity.setAdapter(searchCityAdapter);
            Badd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(search_city.getText()!=null) {
                        cityList.add(search_city.getText().toString());
                        saveData();
                        load();
                        updateView();
                    }
                    else{
                        Toast.makeText(getActivity(),"can't be void",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){
            title_temp.setText(e.toString());
        }

        Bclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                updateView();
            }
        });
    }

    public void updateView(){
        SearchCityAdapter searchCityAdapter=new SearchCityAdapter(cityList);
        RecyclerCity.setAdapter(searchCityAdapter);
    }

    public void load(){
        cityList.clear();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(TemperatureFragment.SHARED_PREFS, getActivity().MODE_PRIVATE);
        String cityListString=sharedPreferences.getString("cityList","");
        String[] itemscityList = cityListString.split(",");
        List<String> items = new ArrayList<String>();
        for ( i = 0; i < itemscityList.length; i++)
        {
            cityList.add(itemscityList[i]);
        }
    }



    public void saveData(){
        StringBuilder stringBuilder =new StringBuilder();
        for( String s : cityList ){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences(SHARED_PREFS,getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cityList", stringBuilder.toString());
        editor.commit();
    }

    public void clear(){
        cityList.clear();
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences(SHARED_PREFS,getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}