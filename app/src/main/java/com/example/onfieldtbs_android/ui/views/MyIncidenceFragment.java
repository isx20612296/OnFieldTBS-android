package com.example.onfieldtbs_android.ui.views;

import static com.example.onfieldtbs_android.utils.Strings.COMPANY;
import static com.example.onfieldtbs_android.utils.Strings.EMPLOYEE;
import static com.example.onfieldtbs_android.utils.Strings.TECHNICIAN;
import static com.example.onfieldtbs_android.utils.Strings.TITLE;

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
import com.example.onfieldtbs_android.databinding.FragmentMyIncidenceBinding;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.ui.viewModels.IncidencesViewModel;
import com.example.onfieldtbs_android.ui.viewModels.LiveInfo;
import com.example.onfieldtbs_android.ui.views.components.IncidenceTableFragment;
import com.example.onfieldtbs_android.utils.OnFieldItemSelected;
import com.example.onfieldtbs_android.utils.SpinnerInfo;
import com.example.onfieldtbs_android.utils.Strings;
import com.example.onfieldtbs_android.utils.filter.Filters;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class MyIncidenceFragment extends Fragment {

    private FragmentMyIncidenceBinding binding;

    private ArrayAdapter<String> statesAdapter;
    private ArrayAdapter<String> prioritiesAdapter;
    private ArrayAdapter<String> datesSortingAdapter;
    private ArrayAdapter<String> searchOptionsAdapter;
    private ArrayList<Incidence> incidences;
    private IncidenceAdapter incidenceAdapter;
    private String state = "none";
    private String priority = "none";
    private Predicate<Incidence> searchFilter = incidence -> true;



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
        incidencesViewModel.getLiveInfo();

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
            state = adapterView.getItemAtPosition(position).toString();
            incidencesViewModel.getDataLiveInfo().observe(getViewLifecycleOwner(), liveInfo -> replaceTable(state, priority, searchFilter, liveInfo));
        });

        binding.myIncidencePrioritySpinner.setOnItemSelectedListener((OnFieldItemSelected) (adapterView, priorityView, position, l) -> {
            priority = adapterView.getItemAtPosition(position).toString();
            incidencesViewModel.getDataLiveInfo().observe(getViewLifecycleOwner(), liveInfo -> replaceTable(state, priority, searchFilter, liveInfo));
        });

        binding.myIncidenceDateSpinner.setOnItemSelectedListener((OnFieldItemSelected) (adapterView, dateView, position, l) -> {

        });

        binding.myIncidenceSearchButton.setOnClickListener(viewSearch -> {
            incidencesViewModel.getDataLiveInfo().observe(getViewLifecycleOwner(), liveInfo -> {
                switch (binding.myIncidenceSearchSpinner.getSelectedItem().toString()) {
                    case TECHNICIAN:
                        searchFilter = Filters.byTechnician(binding.myIncidenceEditText.getText().toString());
                        break;
                    case TITLE:
                        searchFilter = Filters.byTitle(binding.myIncidenceEditText.getText().toString());
                        break;
                    case EMPLOYEE:
                        searchFilter = Filters.byEmployee(binding.myIncidenceEditText.getText().toString());
                        break;
                    case COMPANY:
                        searchFilter = Filters.byCompany(binding.myIncidenceEditText.getText().toString());
                        break;
                    default:
                        searchFilter = incidence -> true;
                }
                replaceTable(state, priority, searchFilter, liveInfo);
            });
        });
    }

    private void replaceTable(String state, String priority, Predicate<Incidence> searchFilter, LiveInfo liveInfo) {
        IncidenceTableFragment incidenceTableFragment = new IncidenceTableFragment(liveInfo.userIncidences.stream()
                .filter(Filters.byNotState("Cerrada"))
                .filter(Filters.byState(state))
                .filter(Filters.byPriority(priority))
                .filter(searchFilter)
                .collect(Collectors.toList()));
        getChildFragmentManager().beginTransaction().replace(R.id.myIncidenceTable, incidenceTableFragment).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}