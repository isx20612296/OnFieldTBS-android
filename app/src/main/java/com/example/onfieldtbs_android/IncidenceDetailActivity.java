package com.example.onfieldtbs_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.onfieldtbs_android.adapter.CommentAdapter;
import com.example.onfieldtbs_android.databinding.ActivityIncidenceDetailBinding;
import com.example.onfieldtbs_android.databinding.ActivityLoginBinding;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.utils.Utils;

import java.io.Serializable;

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

        binding.detailTitle.setText(incidence.getTitle());
        binding.detailCreationData.setText(Utils.formatDateTime(incidence.getCreatedAt()));
        binding.detailClosedData.setText(incidence.getClosedAt() == null ? "" : incidence.getClosedAt());
        binding.detailState.setText(incidence.getState());
        binding.detailPriority.setText(incidence.getPriority());
        binding.detailEmployee.setText(fullEmployeeName);
        binding.detailCompany.setText(incidence.getCompany().getName());
        binding.detailMessage.setText(incidence.getDescription());
        binding.detailTechnician.setText(fullTechnicianName);
        binding.detailUsername.setText(fullTechnicianUserName);
        binding.detailRecycler.setAdapter(new CommentAdapter(incidence.getComments(), getApplicationContext()));
        binding.detailRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }
}