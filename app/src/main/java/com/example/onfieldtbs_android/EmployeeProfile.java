package com.example.onfieldtbs_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.onfieldtbs_android.databinding.ActivityEmployeeProfileBinding;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.service.api.EmployeeService;
import com.example.onfieldtbs_android.service.api.IncidenceService;
import com.example.onfieldtbs_android.ui.components.IncidenceTableFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeProfile extends AppCompatActivity {

    private ActivityEmployeeProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        String employeeId = bundle.getString("employeeId");
        EmployeeService service = new EmployeeService(getApplicationContext());
        service.getEmployeeById(employeeId, employee -> {
            String fullEmployeeName = employee.getName() + " " + employee.getLastname();
            binding.profileEmployeeFullName.setText(fullEmployeeName);
            binding.profileEmployeeCompany.setText(employee.getCompany().getName());
            binding.profileEmployeeExt.setText(employee.getPhoneExt());
            binding.profileEmployeeEmail.setText(employee.getEmail());
            binding.profileEmployeePhone.setText(employee.getDirectPhone());
            IncidenceTableFragment incidenceTableFragment = new IncidenceTableFragment(new ArrayList<>(employee.getIncidences()));
            getSupportFragmentManager().beginTransaction().replace(R.id.profileEmployeeTableFragment, incidenceTableFragment).commit();
        });


    }
}