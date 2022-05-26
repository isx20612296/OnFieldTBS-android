package com.example.onfieldtbs_android.ui.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.service.api.Login;
import com.example.onfieldtbs_android.service.api.Model.ApiClient;
import com.example.onfieldtbs_android.service.api.Model.ModelList;
import com.example.onfieldtbs_android.service.api.Model.RetrofitCallBack;
import com.example.onfieldtbs_android.service.api.Model.WebService;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;


public class IncidencesViewModel extends AndroidViewModel {

    private static final String username = Login.getInstance().getUsername();
    private MutableLiveData<LiveInfo> mutableLiveInfo = new MutableLiveData<>();
    public LiveData<Integer> userIncidencesNumber = Transformations.switchMap(mutableLiveInfo, input -> new MutableLiveData<>(input.userIncidencesNumber));
    public LiveData<Integer> allIncidencesNumber = Transformations.switchMap(mutableLiveInfo, input -> new MutableLiveData<>(input.allIncidencesNumber));

    public IncidencesViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<LiveInfo> getLiveInfo(){
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

}
