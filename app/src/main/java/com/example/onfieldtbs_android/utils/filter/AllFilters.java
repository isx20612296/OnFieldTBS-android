package com.example.onfieldtbs_android.utils.filter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.ui.viewModels.IncidencesViewModel;
import com.example.onfieldtbs_android.ui.viewModels.LiveInfo;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AllFilters implements Filters{
    private Predicate<Incidence> byAllState = incidence -> true;
    private Predicate<Incidence> byAllPriority = incidence -> true;
    private final IncidencesViewModel incidencesViewModel;
    private final LifecycleOwner lifecycleOwner;
    private List<Incidence> incidencesFiltered;

    public AllFilters(IncidencesViewModel incidencesViewModel, LifecycleOwner lifecycleOwner){
        this.incidencesViewModel = incidencesViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    public List<Incidence> addStateFilter(String state){
        byAllState = incidence -> incidence.getState().equals(state);
        return returnList();
    }

    public List<Incidence> deleteStateFilter(){
        byAllState = incidence -> true;
        return returnList();
    }

    public List<Incidence> addPriorityFilter(String priority){
        byAllPriority = incidence -> incidence.getPriority().equals(priority);
        return returnList();
    }

    public List<Incidence> deletePriorityFilter(){
        byAllPriority = incidence -> true;
        return returnList();
    }

    public List<Incidence> returnList(){
        incidencesViewModel.getLiveInfo().observe(lifecycleOwner, liveInfo -> {
            incidencesFiltered = liveInfo.allIncidences.stream()
                    .filter(byAllState)
                    .filter(byAllPriority)
                    .collect(Collectors.toList());
        });
        return incidencesFiltered;
    }
}
