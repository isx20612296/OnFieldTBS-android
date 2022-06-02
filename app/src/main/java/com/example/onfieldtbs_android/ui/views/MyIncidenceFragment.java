package com.example.onfieldtbs_android.ui.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.onfieldtbs_android.R;
import com.example.onfieldtbs_android.adapter.IncidenceAdapter;
import com.example.onfieldtbs_android.databinding.FragmentMyIncidenceBinding;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.ui.viewModels.IncidencesViewModel;
import com.example.onfieldtbs_android.ui.views.components.IncidenceTableFragment;
import com.example.onfieldtbs_android.utils.OnFieldItemSelected;
import com.example.onfieldtbs_android.utils.SpinnerInfo;
import com.example.onfieldtbs_android.utils.filter.Filters;
import com.example.onfieldtbs_android.utils.filter.MyFilters;

import java.util.ArrayList;
import java.util.List;


public class MyIncidenceFragment extends Fragment {

    private FragmentMyIncidenceBinding binding;

    private ArrayAdapter<String> statesAdapter;
    private ArrayAdapter<String> prioritiesAdapter;
    private ArrayAdapter<String> datesSortingAdapter;
    private ArrayAdapter<String> searchOptionsAdapter;
    private ArrayList<Incidence> incidences;
    private IncidenceAdapter incidenceAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyIncidenceBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ViewModel
        final IncidencesViewModel incidencesViewModel = new ViewModelProvider(requireActivity()).get(IncidencesViewModel.class);

        // Filters
        Filters myFilters = new MyFilters(incidencesViewModel, getViewLifecycleOwner());

        // Create Spinner adapters
        statesAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_list, SpinnerInfo.filterStates);
        prioritiesAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_list, SpinnerInfo.filterPriorities);
        datesSortingAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_list, SpinnerInfo.filterDatesSorting);
        searchOptionsAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_list, SpinnerInfo.filterSearchOptions);

        // Set adapters to Spinners
        binding.myIncidenceStateSpinner.setAdapter(statesAdapter);
        binding.myIncidencePrioritySpinner.setAdapter(prioritiesAdapter);
        binding.myIncidenceDateSpinner.setAdapter(datesSortingAdapter);
        binding.myIncidenceSearchSpinner.setAdapter(searchOptionsAdapter);

        // Set default values
        binding.myIncidenceStateSpinner.setSelection(0);
        binding.myIncidencePrioritySpinner.setSelection(0);
        binding.myIncidenceDateSpinner.setSelection(0);
        binding.myIncidenceSearchSpinner.setSelection(0);

        // Set actions to do when item selected
        binding.myIncidenceStateSpinner.setOnItemSelectedListener((OnFieldItemSelected) (adapterView, stateView, position, l) -> {
//            IncidenceTableFragment tableFragment;
//            if (adapterView.getItemAtPosition(position).equals("Estado")) tableFragment = new IncidenceTableFragment(myFilters.deleteStateFilter());
//            else tableFragment = new IncidenceTableFragment(myFilters.addStateFilter(adapterView.getItemAtPosition(position).toString()));
//            getChildFragmentManager().beginTransaction().replace(R.id.myIncidenceTable, tableFragment).commit();
            incidencesViewModel.getDataLiveInfo().observe(getViewLifecycleOwner(), liveInfo -> {
                List<Incidence> list = liveInfo.allIncidences;
                // TODO: Trasladar aqui el codigo del Spinner

            });
        });

        binding.myIncidencePrioritySpinner.setOnItemSelectedListener((OnFieldItemSelected) (adapterView, priorityView, position, l) -> {
//            IncidenceTableFragment tableFragment;
//            if (adapterView.getItemAtPosition(position).equals("Prioridad")) tableFragment = new IncidenceTableFragment(myFilters.deletePriorityFilter());
//            else tableFragment = new IncidenceTableFragment(myFilters.addPriorityFilter(adapterView.getItemAtPosition(position).toString()));
//            getChildFragmentManager().beginTransaction().replace(R.id.myIncidenceTable, tableFragment).commit();
        });

        binding.myIncidenceDateSpinner.setOnItemSelectedListener((OnFieldItemSelected) (adapterView, dateView, position, l) -> {

        });

        binding.myIncidenceSearchSpinner.setOnItemSelectedListener((OnFieldItemSelected) (adapterView, searchView, position, l) -> {
        });


//         Init Recycler view data
       incidencesViewModel.getLiveInfo().observe(getViewLifecycleOwner(), liveInfo -> {
           IncidenceTableFragment tableFragment = new IncidenceTableFragment(liveInfo.userIncidences);
           getChildFragmentManager().beginTransaction().replace(R.id.myIncidenceTable, tableFragment).commit();
       });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}