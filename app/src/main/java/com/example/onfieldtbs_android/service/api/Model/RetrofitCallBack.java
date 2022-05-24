package com.example.onfieldtbs_android.service.api.Model;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface RetrofitCallBack<T> extends Callback<T> {

    @Override
    void onResponse(Call<T> call, Response<T> response);


    @Override
    default void onFailure(Call<T> call, Throwable t){
        Log.e("Throwable ERROR", t.getMessage());
    }
}
