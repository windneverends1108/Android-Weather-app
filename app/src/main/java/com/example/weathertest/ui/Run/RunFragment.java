package com.example.weathertest.ui.Run;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertest.R;
import com.example.weathertest.Module.idealConditionAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RunFragment extends Fragment {

    private TextView title_temp;
    private EditText my_constraint;
    private Button Badd;
    private Button Bclear;
    private RecyclerView RecyclerCondition;
    private ArrayList<String> conditionList;
    int i;
    public static final  String SHARED_PREFS="sharedPrefs";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run,container,false);
        return  view;
    }
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        conditionList=new ArrayList<>();
        load();
        title_temp=view.findViewById(R.id.text_run);
        my_constraint=view.findViewById(R.id.my_constraint);
        Badd=view.findViewById(R.id.button_add_constraint);
        Bclear=view.findViewById(R.id.button_clear_constraint);
        RecyclerCondition=view.findViewById(R.id.idealConstrainRecyclerView);
       //try{
            RecyclerCondition.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            idealConditionAdapter idealConditionAdapter=new idealConditionAdapter(conditionList);
            RecyclerCondition.setAdapter(idealConditionAdapter);
            Badd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(my_constraint.getText()!=null) {
                        conditionList.add(my_constraint.getText().toString());
                        saveData();
                        load();
                        updateView();
                    }
                    else{
                        Toast.makeText(getActivity(),"can't be void",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        //}
        //catch (Exception e){
         //   title_temp.setText(e.toString());
        //}

        Bclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                updateView();
            }
        });
    }

    public void updateView(){
        idealConditionAdapter idealConditionAdapter=new idealConditionAdapter(conditionList);
        RecyclerCondition.setAdapter(idealConditionAdapter);
    }

    public void load(){
        conditionList.clear();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(RunFragment.SHARED_PREFS, getActivity().MODE_PRIVATE);
        String conditionListString=sharedPreferences.getString("conditionList","");
        String[] itemsconditionList = conditionListString.split(",");
        List<String> items = new ArrayList<String>();
        for ( i = 0; i < itemsconditionList.length; i++)
        {
            conditionList.add(itemsconditionList[i]);
        }
    }



    public void saveData(){
        StringBuilder stringBuilder =new StringBuilder();
        for( String s : conditionList ){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences(SHARED_PREFS,getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("conditionList", stringBuilder.toString());
        editor.commit();
    }

    public void clear(){
        conditionList.clear();
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences(SHARED_PREFS,getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}