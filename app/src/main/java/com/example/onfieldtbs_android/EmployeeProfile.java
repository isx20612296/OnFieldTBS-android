package com.example.onfieldtbs_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.onfieldtbs_android.databinding.ActivityEmployeeProfileBinding;
import com.example.onfieldtbs_android.databinding.ActivityLoginBinding;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.service.api.EmployeeService;
import com.example.onfieldtbs_android.service.api.IncidenceService;
import com.example.onfieldtbs_android.service.api.Login;
import com.example.onfieldtbs_android.ui.IncidenceTableFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeProfile extends AppCompatActivity {

    private ActivityEmployeeProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();

        EmployeeService service = new EmployeeService(getApplicationContext());
        service.getEmployeeById(bundle.getString("employeeId"), employee -> {
            String fullEmployeeName = employee.getName() + " " + employee.getLastname();
            binding.profileEmployeeFullName.setText(fullEmployeeName);
            binding.profileEmployeeCompany.setText(employee.getCompany().getName());
            binding.profileEmployeeExt.setText(employee.getPhoneExt());
            binding.profileEmployeeEmail.setText(employee.getEmail());
            binding.profileEmployeePhone.setText(employee.getDirectPhone());
            IncidenceService incidenceService = new IncidenceService(getApplicationContext());
            incidenceService.getAllIncidence(incidences -> {
                    List<Incidence> employeeIncidences = incidences.stream().
                            filter(incidence -> incidence.getEmployee().getId().equals(employee.getId()))
                            .collect(Collectors.toList());
                IncidenceTableFragment incidenceTableFragment = new IncidenceTableFragment(employeeIncidences);
                getSupportFragmentManager().beginTransaction().replace(R.id.profileEmployeeTableFragment, incidenceTableFragment);

            });
        });


    }
}