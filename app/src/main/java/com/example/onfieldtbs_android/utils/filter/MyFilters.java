package com.example.onfieldtbs_android.utils.filter;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;

import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.ui.viewModels.IncidencesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MyFilters implements Filters{

    private Predicate<Incidence> byMyState = incidence -> true;
    private Predicate<Incidence> byMyPriority = incidence -> true;
    private final IncidencesViewModel incidencesViewModel;
    private final LifecycleOwner lifecycleOwner;
    private List<Incidence> incidencesFiltered;

    public MyFilters(IncidencesViewModel incidencesViewModel, LifecycleOwner lifecycleOwner) {
        this.incidencesViewModel = incidencesViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }
    
    public List<Incidence> addStateFilter(String state) {
        byMyState = incidence -> incidence.getState().equals(state);
        return returnList();
    }

    
    public List<Incidence> deleteStateFilter() {
        byMyState = incidence -> true;
        return returnList();
    }

    
    public List<Incidence> addPriorityFilter(String priority) {
        byMyPriority = incidence -> incidence.getPriority().equals(priority);
        return returnList();
    }

    
    public List<Incidence> deletePriorityFilter() {
        byMyPriority = incidence -> true;
        return returnList();
    }

    
    public List<Incidence> returnList() {
        incidencesViewModel.getLiveInfo().observe(lifecycleOwner, liveInfo -> {
            incidencesFiltered = liveInfo.userIncidences.stream()
                    .filter(byMyState)
                    .filter(byMyPriority)
                    .collect(Collectors.toList());
        });
        List<Incidence> emptyList = new ArrayList<>();
        return incidencesFiltered;
    }
}
