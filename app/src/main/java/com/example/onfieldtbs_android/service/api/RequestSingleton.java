package com.example.onfieldtbs_android.service.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestSingleton {

    private static RequestSingleton instance;
    private RequestQueue requestQueue;
    private final Context ctx;


    private RequestSingleton(Context context){
       ctx = context;
       requestQueue = getRequestQueue();
    }


    public static synchronized RequestSingleton getInstance(Context context){
        if (instance == null) {
            instance = new RequestSingleton(context);
        }
        return instance;

    }
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }

}
