package com.example.onfieldtbs_android.ui.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.onfieldtbs_android.R;
import com.example.onfieldtbs_android.adapter.IncidenceAdapter;
import com.example.onfieldtbs_android.databinding.FragmentAllIncidenceBinding;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.ui.viewModels.IncidencesViewModel;
import com.example.onfieldtbs_android.ui.viewModels.LiveInfo;
import com.example.onfieldtbs_android.ui.views.components.IncidenceTableFragment;
import com.example.onfieldtbs_android.utils.OnFieldItemSelected;
import com.example.onfieldtbs_android.utils.SpinnerInfo;
import com.example.onfieldtbs_android.utils.filter.Filters;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class AllIncidenceFragment extends Fragment {

    private FragmentAllIncidenceBinding binding;

    private ArrayAdapter<String> statesAdapter;
    private ArrayAdapter<String> prioritiesAdapter;
    private ArrayAdapter<String> datesSortingAdapter;
    private ArrayAdapter<String> searchOptionsAdapter;
    private ArrayList<Incidence> incidences;
    private IncidenceAdapter incidenceAdapter;
    private String state = "none";
    private String priority = "none";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllIncidenceBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ViewModel
        final IncidencesViewModel incidencesViewModel = new ViewModelProvider(requireActivity()).get(IncidencesViewModel.class);
        incidencesViewModel.getLiveInfo();

        // Create Spinner adapters
        statesAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_list, SpinnerInfo.filterStates);
        prioritiesAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_list, SpinnerInfo.filterPriorities);
        datesSortingAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_list, SpinnerInfo.filterDatesSorting);
        searchOptionsAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_list, SpinnerInfo.filterSearchOptions);

        // Set adapters to Spinners
        binding.allIncidenceStateSpinner.setAdapter(statesAdapter);
        binding.allIncidencePrioritySpinner.setAdapter(prioritiesAdapter);
        binding.allIncidenceDateSpinner.setAdapter(datesSortingAdapter);
        binding.allIncidenceSearchSpinner.setAdapter(searchOptionsAdapter);

        // Set default values
        binding.allIncidenceStateSpinner.setSelection(0);
        binding.allIncidencePrioritySpinner.setSelection(0);
        binding.allIncidenceDateSpinner.setSelection(0);
        binding.allIncidenceSearchSpinner.setSelection(0);

        // Set actions to do when item selected
        binding.allIncidenceStateSpinner.setOnItemSelectedListener((OnFieldItemSelected) (adapterView, stateView, position, l) -> {
            state = adapterView.getItemAtPosition(position).toString();
            incidencesViewModel.getDataLiveInfo().observe(getViewLifecycleOwner(), liveInfo -> replaceTable(state, priority, liveInfo));
        });

        binding.allIncidencePrioritySpinner.setOnItemSelectedListener((OnFieldItemSelected) (adapterView, priorityView, position, l) -> {
            priority = adapterView.getItemAtPosition(position).toString();
            incidencesViewModel.getDataLiveInfo().observe(getViewLifecycleOwner(), liveInfo -> replaceTable(state, priority, liveInfo));
        });
        binding.allIncidenceDateSpinner.setOnItemSelectedListener((OnFieldItemSelected) (adapterView, dateView, position, l) -> {

        });

        binding.allIncidenceSearchSpinner.setOnItemSelectedListener((OnFieldItemSelected) (adapterView, searchView, position, l) -> {

        });
    }

    private void replaceTable(String state, String priority, LiveInfo liveInfo) {
        IncidenceTableFragment incidenceTableFragment = new IncidenceTableFragment(liveInfo.allIncidences.stream().filter(Filters.byPriority(priority)).filter(Filters.byState(state)).collect(Collectors.toList()));
        getChildFragmentManager().beginTransaction().replace(R.id.allIncidenceTable, incidenceTableFragment).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}