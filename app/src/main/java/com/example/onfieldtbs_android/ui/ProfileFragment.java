package com.example.onfieldtbs_android.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onfieldtbs_android.adapter.IncidenceAdapter;
import com.example.onfieldtbs_android.api.Login;
import com.example.onfieldtbs_android.api.WebService;
import com.example.onfieldtbs_android.databinding.FragmentProfileBinding;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.models.Technician;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebService service = new WebService(getContext());

        service.getAllTechnician(technicians -> technicians.forEach(technician -> {
            if (technician.getUser().getUsername().equals(Login.getInstance().getUsername())) {
                service.getTechniciansById(technician.getId().toString(), loggedTechnician -> {
                    String fullName = loggedTechnician.getName() + " " + loggedTechnician.getLastname();
                    String fullUsername = "@" + loggedTechnician.getUser().getUsername();
                    binding.profileFullName.setText(fullName);
                    binding.profileLevel.setText(loggedTechnician.getLevel().getName());
                    binding.profileUsername.setText(fullUsername);
                    binding.profileOnboarding.setText(loggedTechnician.getCreatedAt());
                    binding.profileEmail.setText(loggedTechnician.getEmail());
                    binding.profilePhone.setText(loggedTechnician.getPhone());
//                    loggedTechnician.getIncidences().forEach(incidence -> {
//                        final Array incidenceArray[] = new Array;
//                        service.getIncidenceById(incidence.getId().toString(), fullIncidence -> {
//
//                        });
//                    });
//                    binding.profileRecycler.setAdapter(new IncidenceAdapter(new ArrayList<>(loggedTechnician.getIncidences()), getContext()));
//                    binding.profileRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                });
            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}