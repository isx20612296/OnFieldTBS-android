package com.example.onfieldtbs_android.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onfieldtbs_android.adapter.IncidenceAdapter;
import com.example.onfieldtbs_android.service.api.Login;
import com.example.onfieldtbs_android.service.api.TechnicianService;
import com.example.onfieldtbs_android.databinding.FragmentProfileBinding;


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

        TechnicianService service = new TechnicianService(getContext());

        service.getTechnicianByUsername(Login.getInstance().getUsername(), loggedTechnician -> {
            String fullName = loggedTechnician.getName() + " " + loggedTechnician.getLastname();
            String fullUsername = "@" + loggedTechnician.getUser().getUsername();
            binding.profileFullName.setText(fullName);
            binding.profileLevel.setText(loggedTechnician.getLevel().getName());
            binding.profileUsername.setText(fullUsername);
            binding.profileOnboarding.setText(loggedTechnician.getCreatedAt());
            binding.profileEmail.setText(loggedTechnician.getEmail());
            binding.profilePhone.setText(loggedTechnician.getPhone());
            service.getIncidenceById(loggedTechnician.getId().toString(), myIncidences -> {
                binding.profileRecycler.setAdapter(new IncidenceAdapter(myIncidences, getContext()));
                binding.profileRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}