package com.example.onfieldtbs_android.api.services;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.onfieldtbs_android.api.Login;
import com.example.onfieldtbs_android.api.RequestSingleton;
import com.example.onfieldtbs_android.api.VolleyResponse;

import java.util.HashMap;
import java.util.Map;

public class IncidenceService {
    public static final String BASE_URL = "http://192.168.1.10:8080";
    Context context;

    public IncidenceService(Context context) {
        this.context = context;
    }


    public void login(String username, String password, VolleyResponse<Boolean> vr){
        String url = BASE_URL + "/users/login";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> vr.onResponse(true),
                error -> Toast.makeText(this.context, "ACCESS DENIED", Toast.LENGTH_LONG ).show()
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String credentials = String.format("%s:%s",username,password);
                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                Login login = Login.getInstance(username, password, auth);
                params.put("Authorization", auth);
                return params;
            }
        };
        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }
}


