package com.example.onfieldtbs_android.service.api;

import com.example.onfieldtbs_android.models.Comment;
import com.example.onfieldtbs_android.models.Company;
import com.example.onfieldtbs_android.models.Employee;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.models.Level;
import com.example.onfieldtbs_android.models.Technician;
import com.example.onfieldtbs_android.service.api.Model.ModelList;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;


import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebService {

    // Incidence Service
    @GET("incidences")
    Call<ModelList<Incidence>> getAllIncidences();

    @GET("incidences/{id}")
    Call<Incidence> getIncidenceById(@Path("id") String id);

    @GET("incidences/{id}/comments")
    Call<ModelList<Comment>> getAllCommentsOfIncidence(@Path("id") String id);

    // Level Service
    @GET("levels")
    Call<ModelList<Level>> getAllLevels();

    @GET("levels/{id}")
    Call<Level> getLevelById(@Path("id") String id);

    //TODO(Login)

    // Technician Service
    @GET("technicians")
    Call<ModelList<Technician>> getAllTechnicians();

    @GET("technicians/{id}")
    Call<Technician> getTechnicianById(@Path("id") String id);

    @GET("technicians/search")
    Call<Technician> getTechnicianByUsername(@Query("username") String username);

    @GET("technicians/{id}/incidences")
    Call<ModelList<Incidence>> getIncidenceByTechnicianId(@Path("id") String id);

    // Comment Service

    @GET("comments/{id}")
    Call<Comment> getCommentById(@Path("id") String id);

    // Company Service
    @GET("companies")
    Call<ModelList<Company>> getAllCompanies();

    @GET("companies/{id}")
    Call<Company> getCompanyById(@Path("id") String id);

    // Employee Service+
    @GET("employees")
    Call<ModelList<Employee>> getAllEmployees();

    @GET("employees/{id}")
    Call<Employee> getEmployeeById(@Path("id") String id);
}
