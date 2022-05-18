package com.example.onfieldtbs_android.api.service;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.onfieldtbs_android.api.Login;
import com.example.onfieldtbs_android.api.RequestSingleton;
import com.example.onfieldtbs_android.api.VolleyResponse;
import com.example.onfieldtbs_android.models.Comment;
import com.example.onfieldtbs_android.models.Company;
import com.example.onfieldtbs_android.models.Employee;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.models.Level;
import com.example.onfieldtbs_android.models.Technician;
import com.example.onfieldtbs_android.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncidenceService {
    public static final String BASE_URL = "http://onfieldtbs.ddns.net";
    Context context;

    public IncidenceService(Context context) {
        this.context = context;
    }



    private void toastError(){
        Toast.makeText(context, "AN ERROR OCCURRED", Toast.LENGTH_SHORT).show();
    }

    // INCIDENCES
    public void getAllIncidence(VolleyResponse< List<Incidence> > vr){
        String url = BASE_URL + "/incidences";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    JSONArray jsonArray = response.optJSONArray("result");
                    List<Incidence> incidences = Arrays.asList(new GsonBuilder().create().fromJson(jsonArray.toString(), Incidence[].class));
                        vr.onResponse(incidences);
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

    public void getIncidenceById(String incidenceId,VolleyResponse<Incidence> vr){
        String url = BASE_URL + "/incidences/" + incidenceId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Incidence incidence = new GsonBuilder().create().fromJson(response.toString(), Incidence.class);
                    vr.onResponse(incidence);
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
}


