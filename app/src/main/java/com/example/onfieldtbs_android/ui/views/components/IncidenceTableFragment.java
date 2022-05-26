package com.example.onfieldtbs_android.ui.views.components;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onfieldtbs_android.adapter.IncidenceAdapter;
import com.example.onfieldtbs_android.databinding.FragmentIncidenceBinding;
import com.example.onfieldtbs_android.databinding.FragmentIncidenceTableBinding;
import com.example.onfieldtbs_android.models.Incidence;

import java.util.ArrayList;
import java.util.List;


public class IncidenceTableFragment extends Fragment {

    private List<Incidence> incidenceList = new ArrayList<>();
    private FragmentIncidenceTableBinding binding;


    public IncidenceTableFragment() {
    }

    public IncidenceTableFragment(List<Incidence> incidenceList) {
        this.incidenceList = incidenceList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIncidenceTableBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.incidenceRecycler.setAdapter(new IncidenceAdapter(incidenceList, getContext()));
        binding.incidenceRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}