package com.example.onfieldtbs_android.api;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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

public class WebService {
    public static final String BASE_URL = "http://onfieldtbs.ddns.net";
    Context context;

    public WebService(Context context) {
        this.context = context;
    }



    private void toastError(){
        Toast.makeText(context, "AN ERROR OCCURRED", Toast.LENGTH_SHORT).show();
    }

    // LOGIN
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
                Login.getInstance(username, password, auth);
                params.put("Authorization", Login.getAuth());
                return params;
            }
        };
        RequestSingleton.getInstance(context).addToRequestQueue(request);
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
        );
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


    // LEVELS
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
        );
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

   // TODO: revisar las proyecciones, en incidencia debe devolver un array y no un objeto
    // EMPLOYEES
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

    // TECHNICIAN
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

    // COMPANIES
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


    // COMMENTS
    public void getAllComments(VolleyResponse< List<Comment>> vr){
        String url = BASE_URL + "/comments";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    JSONArray jsonArray = response.optJSONArray("result");
                    List<Comment> comments = Arrays.asList(new GsonBuilder().create().fromJson(jsonArray.toString(), Comment[].class));
                    vr.onResponse(comments);
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

    public void getCommentById(String commentId,VolleyResponse<Comment> vr) {
        String url = BASE_URL + "/comments/" + commentId;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Comment comment = new GsonBuilder().create().fromJson(response.toString(), Comment.class);
                    vr.onResponse(comment);
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


