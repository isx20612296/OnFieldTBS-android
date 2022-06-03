package com.example.onfieldtbs_android.ui.viewModels;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.service.api.Login;
import com.example.onfieldtbs_android.service.api.ApiClient;
import com.example.onfieldtbs_android.service.api.Model.ModelList;
import com.example.onfieldtbs_android.service.api.RetrofitCallBack;
import com.example.onfieldtbs_android.utils.Strings;
import com.example.onfieldtbs_android.utils.Utils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class IncidencesViewModel extends AndroidViewModel {

    private String username;
    private MutableLiveData<LiveInfo> mutableLiveInfo = new MutableLiveData<>();
    private MutableLiveData<List<Incidence>> mutableEmployeeIncidenceList = new MutableLiveData<>();
    public LiveData<Integer> userIncidencesNumber = Transformations.switchMap(mutableLiveInfo, input -> new MutableLiveData<>(input.userIncidencesNumber));
    public LiveData<Integer> allIncidencesNumber = Transformations.switchMap(mutableLiveInfo, input -> new MutableLiveData<>(input.allIncidencesNumber));

    public IncidencesViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<LiveInfo> getLiveInfo(){
        username = getApplication().getSharedPreferences(Strings.PREFERENCES_FILE, Context.MODE_PRIVATE).getString("username","");
        ApiClient.getApi().getAllIncidences().enqueue((RetrofitCallBack<ModelList<Incidence>>) (call, response) -> {
            if (!response.isSuccessful()){
                Log.e("My Incidences Error", response.message());
            }
            Predicate<Incidence> byUsername = i -> username.equals(i.getTechnician().getUser().getUsername());
            assert response.body() != null;
            List<Incidence> myIncidences = response.body().result.stream().filter(byUsername).collect(Collectors.toList());
            LiveInfo liveInfoTemp = new LiveInfo();
            liveInfoTemp.userIncidences = myIncidences;
            liveInfoTemp.userIncidencesNumber = myIncidences.size();
            liveInfoTemp.allIncidences = response.body().result;
            liveInfoTemp.allIncidencesNumber = response.body().result.size();
            mutableLiveInfo.postValue(liveInfoTemp);
        });
        return mutableLiveInfo;
    }

    public MutableLiveData<List<Incidence>> getEmployeeIncidences(String id){
        ApiClient.getApi().getAllIncidences().enqueue((RetrofitCallBack<ModelList<Incidence>>) (call, response) -> {
            if (!response.isSuccessful()){
                Log.e("Employee Incidences Error", response.message());
            }
            Predicate<Incidence> byEmployee = incidence -> incidence.getEmployee().getId().toString().equals(id);
            assert response.body() != null;
            List<Incidence> employeeIncidences = response.body().result.stream().filter(byEmployee).collect(Collectors.toList());
            mutableEmployeeIncidenceList.postValue(employeeIncidences);
        });
        return mutableEmployeeIncidenceList;
    }

    public LiveData<LiveInfo> getDataLiveInfo(){
        return  mutableLiveInfo;
    }
}
