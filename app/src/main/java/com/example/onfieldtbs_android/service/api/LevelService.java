package com.example.onfieldtbs_android.service.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.onfieldtbs_android.service.api.Login;
import com.example.onfieldtbs_android.service.api.RequestSingleton;
import com.example.onfieldtbs_android.service.api.VolleyResponse;
import com.example.onfieldtbs_android.models.Level;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelService {
    public static final String BASE_URL = "http://onfieldtbs.ddns.net";
    Context context;

    public LevelService(Context context) {
        this.context = context;
    }



    private void toastError(){
        Toast.makeText(context, "AN ERROR OCCURRED", Toast.LENGTH_SHORT).show();
    }

    public void getAllLevel(VolleyResponse< List<Level>> vr){
        String url = BASE_URL + "/levels";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    JSONArray jsonArray = response.optJSONArray("result");
                    List<Level> levels = Arrays.asList(new GsonBuilder().create().fromJson(jsonArray.toString(), Level[].class));
                    vr.onResponse(levels);
                },
                error -> toastError()
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", Login.getAuth());
                return params;
            }
        };
        RequestSingleton.getInstance(context).addToRequestQueue(request);

    }

    public void getLevelById(String levelId,VolleyResponse<Level> vr) {
        String url = BASE_URL + "/levels/" + levelId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Level level = new GsonBuilder().create().fromJson(response.toString(), Level.class);
                    vr.onResponse(level);
                },
                error -> toastError()
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", Login.getAuth());
                return params;
            }
        };
        RequestSingleton.getInstance(context).addToRequestQueue(request);

    }

}


