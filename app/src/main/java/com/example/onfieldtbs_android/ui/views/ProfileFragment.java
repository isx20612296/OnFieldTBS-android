package com.example.onfieldtbs_android.ui.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onfieldtbs_android.R;
import com.example.onfieldtbs_android.databinding.FragmentProfileBinding;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.models.Technician;
import com.example.onfieldtbs_android.service.api.Login;
import com.example.onfieldtbs_android.service.api.Model.ApiClient;
import com.example.onfieldtbs_android.service.api.Model.ModelList;
import com.example.onfieldtbs_android.service.api.Model.RetrofitCallBack;
import com.example.onfieldtbs_android.ui.viewModels.IncidencesViewModel;
import com.example.onfieldtbs_android.ui.viewModels.LiveInfo;
import com.example.onfieldtbs_android.ui.views.components.IncidenceTableFragment;

import java.util.List;


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

        ApiClient.getApi().getTechnicianByUsername(Login.getInstance().getUsername()).enqueue((RetrofitCallBack<Technician>) (call, response) -> {
            Technician loggedTechnician = response.body();
            String fullName = loggedTechnician.getName() + " " + loggedTechnician.getLastname();
            String fullUsername = "@" + loggedTechnician.getUser().getUsername();
            binding.profileFullName.setText(fullName);
            binding.profileLevel.setText(loggedTechnician.getLevel().getName());
            binding.profileUsername.setText(fullUsername);
            binding.profileOnboarding.setText(loggedTechnician.getCreatedAt());
            binding.profileEmail.setText(loggedTechnician.getEmail());
            binding.profilePhone.setText(loggedTechnician.getPhone());

            // View Model
            final IncidencesViewModel incidencesViewModel = new ViewModelProvider(requireActivity()).get(IncidencesViewModel.class);

            // Set technician table data
            incidencesViewModel.getLiveInfo().observe(getViewLifecycleOwner(), liveInfo -> {
                IncidenceTableFragment tableFragment = new IncidenceTableFragment(liveInfo.userIncidences);
                getChildFragmentManager().beginTransaction().replace(R.id.profileTableFragment, tableFragment).commit();
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}