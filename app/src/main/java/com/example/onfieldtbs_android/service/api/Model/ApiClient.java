package com.example.onfieldtbs_android.service.api.Model;

import android.util.Base64;

import com.example.onfieldtbs_android.service.api.Login;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", Login.getAuth())
                        .build();
                return chain.proceed(newRequest);
            }).build();


    public static WebService getApi(){
        return  new Retrofit.Builder()
                .client(client)
                .baseUrl("http://onfieldtbs.ddns.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebService.class);
    }
}
