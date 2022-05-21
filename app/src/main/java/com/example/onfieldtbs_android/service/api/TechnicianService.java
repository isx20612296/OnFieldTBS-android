package com.example.onfieldtbs_android.service.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.onfieldtbs_android.service.api.Login;
import com.example.onfieldtbs_android.service.api.RequestSingleton;
import com.example.onfieldtbs_android.service.api.VolleyResponse;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.models.Technician;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TechnicianService {
    public static final String BASE_URL = "http://onfieldtbs.ddns.net";
    Context context;

    public TechnicianService(Context context) {
        this.context = context;
    }



    private void toastError(){
        Toast.makeText(context, "AN ERROR OCCURRED", Toast.LENGTH_SHORT).show();
    }

    public void getAllTechnician(VolleyResponse< List<Technician>> vr){
        String url = BASE_URL + "/technicians";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    JSONArray jsonArray = response.optJSONArray("result");
                    List<Technician> technicians = Arrays.asList(new GsonBuilder().create().fromJson(jsonArray.toString(), Technician[].class));
                    vr.onResponse(technicians);
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

    public void getTechniciansById(String technicianId,VolleyResponse<Technician> vr) {
        String url = BASE_URL + "/technicians/" + technicianId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Technician technician = new GsonBuilder().create().fromJson(response.toString(), Technician.class);
                    vr.onResponse(technician);
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

    public void getTechnicianByUsername(String username,VolleyResponse<Technician> vr) {
        String url = BASE_URL + "/technicians/search?username=" + username;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Technician technician = new GsonBuilder().create().fromJson(response.toString(), Technician.class);
                    vr.onResponse(technician);
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

    public void getIncidenceById(String id, VolleyResponse< List<Incidence>> vr){
        String url = BASE_URL + "/technicians/" + id + "/incidences";
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
}


