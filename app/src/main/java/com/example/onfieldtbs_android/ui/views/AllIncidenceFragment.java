package com.example.onfieldtbs_android.ui.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.onfieldtbs_android.R;
import com.example.onfieldtbs_android.adapter.IncidenceAdapter;
import com.example.onfieldtbs_android.databinding.FragmentAllIncidenceBinding;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.ui.viewModels.IncidencesViewModel;
import com.example.onfieldtbs_android.ui.views.components.IncidenceTableFragment;
import com.example.onfieldtbs_android.utils.filter.AllFilters;
import com.example.onfieldtbs_android.utils.OnFieldItemSelected;
import com.example.onfieldtbs_android.utils.SpinnerInfo;
import com.example.onfieldtbs_android.utils.filter.Filters;

import java.util.ArrayList;


public class AllIncidenceFragment extends Fragment {

    private FragmentAllIncidenceBinding binding;

    private ArrayAdapter<String> statesAdapter;
    private ArrayAdapter<String> prioritiesAdapter;
    private ArrayAdapter<String> datesSortingAdapter;
    private ArrayAdapter<String> searchOptionsAdapter;
    private ArrayList<Incidence> incidences;
    private IncidenceAdapter incidenceAdapter;



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

        // Filters
        Filters allFilters = new AllFilters(incidencesViewModel, getViewLifecycleOwner());

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
            IncidenceTableFragment tableFragment;
            if (adapterView.getItemAtPosition(position).equals("Estado")) tableFragment = new IncidenceTableFragment(allFilters.deleteStateFilter());
            else tableFragment = new IncidenceTableFragment(allFilters.addStateFilter(adapterView.getItemAtPosition(position).toString()));
            getChildFragmentManager().beginTransaction().replace(R.id.allIncidenceTable, tableFragment).commit();
        });

        binding.allIncidencePrioritySpinner.setOnItemSelectedListener((OnFieldItemSelected) (adapterView, priorityView, position, l) -> {
            IncidenceTableFragment tableFragment;
            if (adapterView.getItemAtPosition(position).equals("Prioridad")) tableFragment = new IncidenceTableFragment(allFilters.deletePriorityFilter());
            else tableFragment = new IncidenceTableFragment(allFilters.addPriorityFilter(adapterView.getItemAtPosition(position).toString()));
            getChildFragmentManager().beginTransaction().replace(R.id.allIncidenceTable, tableFragment).commit();
        });

        binding.allIncidenceDateSpinner.setOnItemSelectedListener((OnFieldItemSelected) (adapterView, dateView, position, l) -> {

        });

        binding.allIncidenceSearchSpinner.setOnItemSelectedListener((OnFieldItemSelected) (adapterView, searchView, position, l) -> {

        });




//         Init Recycler view data
//        incidencesViewModel.getLiveInfo().observe(getViewLifecycleOwner(), liveInfo -> {
//            IncidenceTableFragment tableFragment = new IncidenceTableFragment(liveInfo.allIncidences);
//            getChildFragmentManager().beginTransaction().replace(R.id.allIncidenceTable, tableFragment).commit();
//        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}