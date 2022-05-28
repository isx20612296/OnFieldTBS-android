package com.example.onfieldtbs_android.ui.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onfieldtbs_android.R;
import com.example.onfieldtbs_android.adapter.ViewPagerIncidenceFragmentAdapter;
import com.example.onfieldtbs_android.databinding.FragmentIncidenceBinding;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.service.api.Model.ApiClient;
import com.example.onfieldtbs_android.service.api.Model.ModelList;
import com.example.onfieldtbs_android.service.api.Model.RetrofitCallBack;
import com.example.onfieldtbs_android.service.api.Model.WebService;
import com.example.onfieldtbs_android.ui.viewModels.IncidencesViewModel;
import com.example.onfieldtbs_android.ui.viewModels.LiveInfo;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


public class IncidenceFragment extends Fragment {

    private FragmentIncidenceBinding binding;
    ViewPagerIncidenceFragmentAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIncidenceBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ViewPagerIncidenceFragmentAdapter(this);
        binding.viewPager.setAdapter(adapter);
        IncidencesViewModel incidencesViewModel = new ViewModelProvider(requireActivity()).get(IncidencesViewModel.class);
        new TabLayoutMediator(binding.tabIncidences, binding.viewPager, (tab, position) -> {
            switch (position){
                case 0: {
                    tab.setText("Mis Incidencias");
                    tab.setIcon(R.drawable.ic_technician);
                    BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                    badgeDrawable.setBackgroundColor(getResources().getColor(R.color.primaryColor, null));
                    badgeDrawable.setVisible(true);
                    incidencesViewModel.userIncidencesNumber.observe(getViewLifecycleOwner(), badgeDrawable::setNumber);;
                    break;
                }
                case 1: {
                    tab.setText("Todas");
                    tab.setIcon(R.drawable.ic_all_incidences);
                    BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                    badgeDrawable.setBackgroundColor(getResources().getColor(R.color.primaryColor, null));
                    badgeDrawable.setVisible(true);
                    incidencesViewModel.allIncidencesNumber.observe(getViewLifecycleOwner(), badgeDrawable::setNumber);
                    break;
                }
            }
        }).attach();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}