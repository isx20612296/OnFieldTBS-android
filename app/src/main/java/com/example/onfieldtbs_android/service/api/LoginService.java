package com.example.onfieldtbs_android.service.api;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.onfieldtbs_android.service.api.Login;
import com.example.onfieldtbs_android.service.api.RequestSingleton;
import com.example.onfieldtbs_android.service.api.VolleyResponse;

import java.util.HashMap;
import java.util.Map;

public class LoginService {
    public static final String BASE_URL = "http://onfieldtbs.ddns.net";
    Context context;

    public LoginService(Context context) {
        this.context = context;
    }



    private void toastError(){
        Toast.makeText(context, "AN ERROR OCCURRED", Toast.LENGTH_SHORT).show();
    }

    // LOGIN
    public void login(String username, String password, VolleyResponse<Boolean> vr) {
        String url = BASE_URL + "/users/login";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> vr.onResponse(true),
                error -> Toast.makeText(this.context, "ACCESS DENIED", Toast.LENGTH_LONG).show()
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String credentials = String.format("%s:%s", username, password);
                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                Login.initInstance(username, password, auth);
                params.put("Authorization", Login.getAuth());
                return params;
            }
        };
        RequestSingleton.getInstance(context).addToRequestQueue(request);
    }
}


