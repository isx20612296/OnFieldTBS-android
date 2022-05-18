package com.example.onfieldtbs_android.api.service;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.onfieldtbs_android.api.Login;
import com.example.onfieldtbs_android.api.RequestSingleton;
import com.example.onfieldtbs_android.api.VolleyResponse;
import com.example.onfieldtbs_android.models.Employee;
import com.example.onfieldtbs_android.models.Incidence;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {
    public static final String BASE_URL = "http://onfieldtbs.ddns.net";
    Context context;

    public EmployeeService(Context context) {
        this.context = context;
    }



    private void toastError(){
        Toast.makeText(context, "AN ERROR OCCURRED", Toast.LENGTH_SHORT).show();
    }

    public void getAllEmployees(VolleyResponse< List<Employee>> vr){
        String url = BASE_URL + "/employees";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    JSONArray jsonArray = response.optJSONArray("result");
                    List<Employee> employees = Arrays.asList(new GsonBuilder().create().fromJson(jsonArray.toString(), Employee[].class));
                    vr.onResponse(employees);
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

    public void getEmployeeById(String employeelId,VolleyResponse<Employee> vr) {
        String url = BASE_URL + "/employees/" + employeelId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Employee employee = new GsonBuilder().create().fromJson(response.toString(), Employee.class);
                    vr.onResponse(employee);
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


