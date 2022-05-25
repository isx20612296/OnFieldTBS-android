package com.example.onfieldtbs_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.onfieldtbs_android.databinding.ActivityEmployeeProfileBinding;
import com.example.onfieldtbs_android.models.Employee;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.service.api.Model.ApiClient;
import com.example.onfieldtbs_android.service.api.Model.ModelList;
import com.example.onfieldtbs_android.service.api.Model.RetrofitCallBack;
import com.example.onfieldtbs_android.ui.components.IncidenceTableFragment;

import java.util.List;
import java.util.function.Predicate;
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
        ApiClient.getApi().getEmployeeById(employeeId).enqueue((RetrofitCallBack<Employee>) (call, response) -> {
            Employee employee = response.body();
            String fullEmployeeName = employee.getName() + " " + employee.getLastname();
            binding.profileEmployeeFullName.setText(fullEmployeeName);
            binding.profileEmployeeCompany.setText(employee.getCompany().getName());
            binding.profileEmployeeExt.setText(employee.getPhoneExt());
            binding.profileEmployeeEmail.setText(employee.getEmail());
            binding.profileEmployeePhone.setText(employee.getDirectPhone());
            ApiClient.getApi().getAllIncidences().enqueue((RetrofitCallBack<ModelList<Incidence>>) (incidenceCall, incidenceResponse) -> {
                Predicate<Incidence> byEmployee = incidence -> incidence.getEmployee().getId().equals(employee.getId());
                List<Incidence> employeeIncidences = incidenceResponse.body().result.stream().filter(byEmployee).collect(Collectors.toList());
                IncidenceTableFragment incidenceTableFragment = new IncidenceTableFragment(employeeIncidences);
                getSupportFragmentManager().beginTransaction().replace(R.id.profileEmployeeTableFragment, incidenceTableFragment).commit();
            });
        });
    }
}