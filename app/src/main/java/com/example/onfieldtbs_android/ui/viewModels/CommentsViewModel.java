package com.example.onfieldtbs_android.ui.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onfieldtbs_android.models.Comment;
import com.example.onfieldtbs_android.service.api.ApiClient;
import com.example.onfieldtbs_android.service.api.Model.ModelList;
import com.example.onfieldtbs_android.service.api.RetrofitCallBack;
import com.example.onfieldtbs_android.utils.mappers.CommentDate;

import java.util.List;

public class CommentsViewModel extends ViewModel {

    private MutableLiveData<List<CommentDate>> commentsData = new MutableLiveData<>();
    private MutableLiveData<IncidenceStatePriority> mutableStatePriority = new MutableLiveData<>();


    public void getAllCommentOfIncidence(String incidenceId){
        ApiClient.getApi().getAllCommentsOfIncidence(incidenceId)
                .enqueue((RetrofitCallBack<ModelList<Comment>>) (call, response) -> {
                    if (!response.isSuccessful()){
                        Log.e("Error load comments of " + incidenceId, response.message());
                    }
                    assert response.body() != null;
                    List<CommentDate> comments = CommentDate.toListOfCommentWithDateType(response.body().result);
                    commentsData.postValue(comments);
                });
    }

    public LiveData<List<CommentDate>> readComments(){
        return commentsData;
    }
}
