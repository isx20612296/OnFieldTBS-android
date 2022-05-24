package com.example.onfieldtbs_android.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.onfieldtbs_android.R;
import com.example.onfieldtbs_android.adapter.IncidenceAdapter;
import com.example.onfieldtbs_android.databinding.FragmentAllIncidenceBinding;
import com.example.onfieldtbs_android.databinding.FragmentIncidenceBinding;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.service.api.IncidenceService;
import com.example.onfieldtbs_android.service.api.Login;
import com.example.onfieldtbs_android.ui.components.IncidenceTableFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


public class AllIncidenceFragment extends Fragment {

    private FragmentAllIncidenceBinding binding;

    private String [] states;
    private String [] priorities;
    private String [] datesSorting;
    private String [] searchOptions;
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



        IncidenceService service = new IncidenceService(getContext());

        // Set lists data
        states = new String[]{"Estado", "Abierto", "En progreso", "Pausado", "Cerrado"};
        priorities = new String[] {"Prioridad", "Alta", "Media", "Baja"};
        datesSorting = new String[]{"Fecha", "Recientes", "Antiguos"};
        searchOptions = new String[]{"Buscar por...", "Título", "Técnico", "Empleado", "Empresa"};

        // Create Spinner adapters
        statesAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_list, states);
        prioritiesAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_list, priorities);
        datesSortingAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_list, datesSorting);
        searchOptionsAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_list, searchOptions);

        // Set adapters to Spinners
        binding.incidenceStateSpinner.setAdapter(statesAdapter);
        binding.incidencePrioritySpinner.setAdapter(prioritiesAdapter);
        binding.incidenceDateSpinner.setAdapter(datesSortingAdapter);
        binding.incidenceSearchSpinner.setAdapter(searchOptionsAdapter);

        // Set default values
        binding.incidenceStateSpinner.setSelection(0);
        binding.incidencePrioritySpinner.setSelection(0);
        binding.incidenceDateSpinner.setSelection(0);
        binding.incidenceSearchSpinner.setSelection(0);

        // Set actions to do when item selected
        binding.incidenceStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("INCIDENCE", "Selected: " + adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("INCIDENCE", "Selected: NONE");
            }
        });

        binding.incidencePrioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("INCIDENCE", "Selected: " + adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("INCIDENCE", "Selected: NONE");
            }
        });

        binding.incidenceDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("INCIDENCE", "Selected: " + adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("INCIDENCE", "Selected: NONE");
            }
        });

        binding.incidenceSearchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("INCIDENCE", "Selected: " + adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("INCIDENCE", "Selected: NONE");
            }
        });


        // Init Recycler view data
        service.getAllIncidence(incidenceList -> {
            IncidenceTableFragment tableFragment = new IncidenceTableFragment(incidenceList);
            getChildFragmentManager().beginTransaction().replace(R.id.incidenceTable, tableFragment).commit();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}