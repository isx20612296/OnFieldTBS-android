package com.example.onfieldtbs_android.utils;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

public interface OnFieldItemSelected extends AdapterView.OnItemSelectedListener{
    @Override
    void onItemSelected(AdapterView<?> adapterView, View view, int position, long l);

    @Override
    default void onNothingSelected(AdapterView<?> adapterView){
        Log.d("INCIDENCE", "Selected: NONE");
    };
}
