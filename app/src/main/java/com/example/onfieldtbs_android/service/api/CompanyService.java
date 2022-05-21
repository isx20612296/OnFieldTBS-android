package com.example.onfieldtbs_android.service.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.onfieldtbs_android.service.api.Login;
import com.example.onfieldtbs_android.service.api.RequestSingleton;
import com.example.onfieldtbs_android.service.api.VolleyResponse;
import com.example.onfieldtbs_android.models.Company;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyService {
    public static final String BASE_URL = "http://onfieldtbs.ddns.net";
    Context context;

    public CompanyService(Context context) {
        this.context = context;
    }


    private void toastError() {
        Toast.makeText(context, "AN ERROR OCCURRED", Toast.LENGTH_SHORT).show();
    }

    public void getAllCompanies(VolleyResponse< List<Company>> vr){
        String url = BASE_URL + "/companies";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    JSONArray jsonArray = response.optJSONArray("result");
                    List<Company> companies = Arrays.asList(new GsonBuilder().create().fromJson(jsonArray.toString(), Company[].class));
                    vr.onResponse(companies);
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

    public void getCompanyById(String companyId,VolleyResponse<Company> vr) {
        String url = BASE_URL + "/companies/" + companyId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Company company = new GsonBuilder().create().fromJson(response.toString(), Company.class);
                    vr.onResponse(company);
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


