package com.example.onfieldtbs_android.ui.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onfieldtbs_android.dto.CompanyDto;
import com.example.onfieldtbs_android.service.api.ApiClient;
import com.example.onfieldtbs_android.service.api.Model.ModelList;
import com.example.onfieldtbs_android.service.api.RetrofitCallBack;

import java.util.List;

public class CompanyViewModel extends ViewModel {
    private MutableLiveData<List<CompanyDto>> companyList = new MutableLiveData<>();

    public void getCompaniesInfo(){
        ApiClient.getApi().getCompaniesInfo().enqueue((RetrofitCallBack<ModelList<CompanyDto>>) (call, response) -> {
            if (!response.isSuccessful()){
                assert response.errorBody() != null;
                Log.e("Error load companies", response.errorBody().toString());
            }
            assert response.body() != null;
            companyList.postValue(response.body().result);
        });
    }

    public LiveData<List<CompanyDto>> readCompanies(){
        return companyList;
    }
}
