package com.example.onfieldtbs_android.utils.filter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.ui.viewModels.IncidencesViewModel;
import com.example.onfieldtbs_android.ui.viewModels.LiveInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Filters {

    public static Predicate<Incidence> byState(String state) {
        if (state.equals("Estado")) return incidence -> true;
        return incidence -> incidence.getStatus().equals(state);
    }

    public static Predicate<Incidence> byNotState(String state) {
        return incidence -> !incidence.getStatus().equals(state);
    }

    public static Predicate<Incidence> byPriority(String priority) {
        if (priority.equals("Prioridad")) return incidence -> true;
        return incidence -> incidence.getPriority().equals(priority);
    }

    public static Predicate<Incidence> byTechnician(String name){
        if (name.equals("")) return incidence -> true;
        return incidence -> incidence.getTechnician().getName().toLowerCase().contains(name) || incidence.getTechnician().getLastname().toLowerCase().contains(name);
    }

    public static Predicate<Incidence> byTitle(String name){
        if (name.equals("")) return incidence -> true;
        return incidence -> incidence.getTitle().toLowerCase().contains(name);
    }

    public static Predicate<Incidence> byEmployee(String name){
        if (name.equals("")) return incidence -> true;
        return incidence -> incidence.getEmployee().getName().toLowerCase().contains(name);
    }

    public static Predicate<Incidence> byCompany(String name){
        if (name.equals("")) return incidence -> true;
        return incidence -> incidence.getCompany().getName().toLowerCase().contains(name);
    }


}
