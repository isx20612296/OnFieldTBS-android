package com.example.onfieldtbs_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.onfieldtbs_android.adapter.CommentAdapter;
import com.example.onfieldtbs_android.databinding.ActivityIncidenceDetailBinding;
import com.example.onfieldtbs_android.models.Employee;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.models.Technician;
import com.example.onfieldtbs_android.service.api.Login;
import com.example.onfieldtbs_android.service.api.Model.ApiClient;
import com.example.onfieldtbs_android.service.api.Model.ModelList;
import com.example.onfieldtbs_android.service.api.Model.RetrofitCallBack;
import com.example.onfieldtbs_android.ui.viewModels.IncidencesViewModel;
import com.example.onfieldtbs_android.utils.SpinnerInfo;
import com.example.onfieldtbs_android.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;


public class IncidenceDetailActivity extends AppCompatActivity {

    private ActivityIncidenceDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIncidenceDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();

        if (bundle.getSerializable("incidence") == null){
            Log.i("SERIALIZABLE", "IS NULL INCIDENCE");
            return;
        }

        Incidence incidence = (Incidence) bundle.getSerializable("incidence");

        String fullEmployeeName = incidence.getEmployee().getName() + " " + incidence.getEmployee().getLastname();
        String fullTechnicianName = incidence.getTechnician().getName() + " " + incidence.getTechnician().getLastname();
        String fullTechnicianUserName = "@" + incidence.getTechnician().getUser().getUsername();

        // State and Priority buttons
        binding.detailState.setOnClickListener(view -> showAlertDialog("Estado", SpinnerInfo.detailStates, binding.detailState));
        binding.detailPriority.setOnClickListener(view -> showAlertDialog("Prioridad", SpinnerInfo.detailPriorities, binding.detailPriority));

        // Technician button
        ApiClient.getApi().getAllTechnicians().enqueue((RetrofitCallBack<ModelList<Technician>>) (call, response) -> {
            assert response.body() != null;
            List<Technician> techniciansReduced = response.body().result.stream().map(technician -> {
                Technician technicianTmp = new Technician();
                technicianTmp.setUser(technician.getUser());
                technicianTmp.setName(technician.getName());
                technicianTmp.setLastname(technician.getLastname());
                return technicianTmp;
            }).collect(Collectors.toList());
            binding.detailCardTechnician.setOnClickListener(view -> showTechnicianAlertDialog(techniciansReduced, binding.detailTechnician));
        });


        binding.detailTitle.setText(incidence.getTitle());
        binding.detailCreationData.setText(Utils.formatDateTime(incidence.getCreatedAt()));
        binding.detailClosedData.setText(incidence.getClosedAt() == null ? "" : incidence.getClosedAt());
        binding.detailState.setText(incidence.getState());
        binding.detailPriority.setText(incidence.getPriority());
        setButtonColor(incidence.getPriority(), binding.detailPriority);
        binding.detailEmployee.setText(fullEmployeeName);
        binding.detailCompany.setText(incidence.getCompany().getName());
        binding.detailMessage.setText(incidence.getDescription());
        binding.detailTechnician.setText(fullTechnicianName);
        binding.detailUsername.setText(fullTechnicianUserName);
        binding.detailRecycler.setAdapter(new CommentAdapter(incidence.getComments(), getApplicationContext()));
        binding.detailRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Employee Detail On Click Listener
        binding.detailCardEmployee.setOnClickListener(view -> {
            Intent intent = new Intent(IncidenceDetailActivity.this, EmployeeProfile.class);
            Bundle employeeBundle = new Bundle();
            employeeBundle.putString("employeeId", incidence.getEmployee().getId().toString());
            intent.putExtras(employeeBundle);
            startActivity(intent);
        });

    }

    private void showAlertDialog(String title, String[] detailData, Button button) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(detailData, (dialogInterface, i) -> {
            button.setText(detailData[i]);
            setButtonColor(detailData[i], button);
            // POST ACTIONS


            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void showTechnicianAlertDialog(List<Technician> technicianReduced, TextView technicianText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Técnicos");

        String[] namesArray = technicianReduced.stream()
                .map(technician -> technician.getName() + " " + technician.getLastname())
                .toArray(String[]::new);

        builder.setItems(namesArray, (dialogInterface, i) -> {
            binding.detailTechnician.setText(namesArray[i]);
            binding.detailUsername.setText("@" + technicianReduced.get(i).getUser().getUsername());
            // POST ACTIONS


            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setButtonColor(String detailData, Button button) {
        switch (detailData){
            case "Baja":
                button.setTextColor(getResources().getColor(R.color.green, null));
                break;
            case "Media":
                button.setTextColor(getResources().getColor(R.color.orange, null));
                break;
            case "Alta":
                button.setTextColor(getResources().getColor(R.color.red, null));
                break;
        }
    }
}