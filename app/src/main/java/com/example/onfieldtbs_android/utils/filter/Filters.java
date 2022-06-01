package com.example.onfieldtbs_android.utils.filter;

import com.example.onfieldtbs_android.models.Incidence;

import java.util.List;

public interface Filters {
    List<Incidence> addStateFilter(String state);
    List<Incidence> deleteStateFilter();
    List<Incidence> addPriorityFilter(String priority);
    List<Incidence> deletePriorityFilter();
    List<Incidence> returnList();
}
