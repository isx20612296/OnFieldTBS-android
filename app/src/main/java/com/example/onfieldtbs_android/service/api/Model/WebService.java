package com.example.onfieldtbs_android.service.api.Model;

import com.example.onfieldtbs_android.models.Incidence;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WebService {

    @GET("incidences")
   Call<ModelList<Incidence>> getAllIncidences();

    @GET("incidences/{id}")
    Call<Incidence> getIncidenceById(@Path("id") String id);
}
