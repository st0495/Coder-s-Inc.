package com.example.iway.codersinc.Contests;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.iway.codersinc.R;


public class CollegeContests extends Fragment implements View.OnClickListener {
    TextView text;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.collegecontests_layout,container,false);
        text=(TextView)v.findViewById(R.id.text_college);
        text.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {

        if(text.getVisibility()==View.INVISIBLE){
            text.setVisibility(View.VISIBLE);
        }
        else
        {
            text.setVisibility(View.VISIBLE);
        }
    }
}