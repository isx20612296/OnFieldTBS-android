package com.example.onfieldtbs_android.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.onfieldtbs_android.R;

import com.example.onfieldtbs_android.adapter.IncidenceAdapter;
import com.example.onfieldtbs_android.api.WebService;
import com.example.onfieldtbs_android.databinding.FragmentIncidenceBinding;
import com.example.onfieldtbs_android.models.Comment;
import com.example.onfieldtbs_android.models.Company;
import com.example.onfieldtbs_android.models.Employee;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.models.Level;
import com.example.onfieldtbs_android.models.Technician;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;


public class IncidenceFragment extends Fragment {

    private FragmentIncidenceBinding binding;

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
        binding = FragmentIncidenceBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TESTING ################################################################
//        NavController navController = Navigation.findNavController(view);
//
//        binding.testButton.setOnClickListener(viewB -> {
//            navController.navigate(R.id.action_incidenceFragment_to_incidenceDetailFragment);
//        });
        // TESTING ################################################################



        // View all incidences Button
        AtomicBoolean showMyIncidences = new AtomicBoolean(true);
        binding.incidenceAll.setOnClickListener(view1 -> {
            if (showMyIncidences.get()){
                binding.incidenceTitle.setText("Todas las Incidencias");
                binding.incidenceAll.setText("Ver asignadas");
                showMyIncidences.set(false);
            } else {
                binding.incidenceTitle.setText("Mis Incidencias");
                binding.incidenceAll.setText("Ver todas");
                showMyIncidences.set(true);
            }
        });

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
        incidences = new ArrayList<>();
        Incidence incidence1 = new Incidence();
        incidence1.setTitle("Problema con el portatil");
        incidence1.setState("Abierto");
        incidence1.setPriority("Baja");
        incidence1.setCreatedAt(LocalDateTime.of(2022, 3 ,2 ,16, 48).toString());
        Incidence incidence2 = new Incidence();
        incidence2.setTitle("Problema con el portatil");
        incidence2.setState("Abierto");
        incidence2.setPriority("Baja");
        incidence1.setCreatedAt(LocalDateTime.of(2022, 3 ,2 ,16, 48).toString());
        Incidence incidence3 = new Incidence();
        incidence3.setTitle("Problema con el portatil");
        incidence3.setState("Abierto");
        incidence3.setPriority("Baja");
        incidence1.setCreatedAt(LocalDateTime.of(2022, 3 ,2 ,16, 48).toString());
        Incidence incidence4 = new Incidence();
        incidence4.setTitle("Problema con el portatil");
        incidence4.setState("Abierto");
        incidence4.setPriority("Baja");
        incidence1.setCreatedAt(LocalDateTime.of(2022, 3 ,2 ,16, 48).toString());
        Incidence incidence5 = new Incidence();
        incidence5.setTitle("Problema con el portatil");
        incidence5.setState("Abierto");
        incidence5.setPriority("Baja");
        incidence1.setCreatedAt(LocalDateTime.of(2022, 3 ,2 ,16, 48).toString());
        incidences.addAll(Arrays.asList(incidence1, incidence2, incidence3, incidence4, incidence5));

        incidenceAdapter = new IncidenceAdapter(incidences, getContext());
        binding.incidenceRecycler.setAdapter(incidenceAdapter);
        binding.incidenceRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}