package com.example.onfieldtbs_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onfieldtbs_android.adapter.CommentAdapter;
import com.example.onfieldtbs_android.databinding.ActivityIncidenceDetailBinding;
import com.example.onfieldtbs_android.models.Comment;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.models.Technician;
import com.example.onfieldtbs_android.service.api.ApiClient;
import com.example.onfieldtbs_android.service.api.Model.ModelList;
import com.example.onfieldtbs_android.service.api.RetrofitCallBack;
import com.example.onfieldtbs_android.ui.viewModels.CommentsViewModel;
import com.example.onfieldtbs_android.ui.viewModels.IncidencesViewModel;
import com.example.onfieldtbs_android.utils.SpinnerInfo;
import com.example.onfieldtbs_android.utils.Utils;
import com.example.onfieldtbs_android.utils.mappers.CommentDate;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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
            binding.detailCardTechnician.setOnClickListener(view -> showTechnicianAlertDialog(techniciansReduced));
        });


        binding.detailTitle.setText(incidence.getTitle());
        binding.detailCreationData.setText(Utils.formatDateTime(incidence.getCreatedAt()));
        binding.detailClosedData.setText(incidence.getClosedAt() == null ? "" : incidence.getClosedAt());
        binding.detailState.setText(incidence.getStatus());
        binding.detailPriority.setText(incidence.getPriority());
        setButtonColor(incidence.getPriority(), binding.detailPriority);
        binding.detailEmployee.setText(fullEmployeeName);
        binding.detailCompany.setText(incidence.getCompany().getName());
        binding.detailMessage.setText(incidence.getDescription());
        binding.detailTechnician.setText(fullTechnicianName);
        binding.detailUsername.setText(fullTechnicianUserName);

        // #####################################################################################################################
        // Comments

        CommentsViewModel commentsViewModel = new ViewModelProvider(this).get(CommentsViewModel.class);
        commentsViewModel.getAllCommentOfIncidence(incidence.getId().toString());
        commentsViewModel.readComments().observe(this, comments ->{
            comments.forEach(c -> System.out.println(c.createdAt + " -> " + c.getMessage()));
            binding.detailRecycler.setAdapter(new CommentAdapter(comments, getApplicationContext()));
            binding.detailRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        } );
        // #####################################################################################################################


        // Employee Detail On Click Listener
        binding.detailCardEmployee.setOnClickListener(view -> {
            Intent intent = new Intent(IncidenceDetailActivity.this, EmployeeProfile.class);
            Bundle employeeBundle = new Bundle();
            employeeBundle.putString("employeeId", incidence.getEmployee().getId().toString());
            intent.putExtras(employeeBundle);
            startActivity(intent);
        });

        // Add Comment button Listener
        binding.detailAddComment.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            EditText editText = new EditText(this);
            builder.setTitle("Escribe tu comentario");
            builder.setView(editText);
            builder.setPositiveButton("Publicar", (dialogInterface, i) -> {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Error: El comentario está vacío", Toast.LENGTH_SHORT).show();
                    return;
                }
                // POST ACTIONS
//                binding.detailRecycler.setAdapter(new CommentAdapter(incidence.getComments(), getApplicationContext()));
//                binding.detailRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                dialogInterface.dismiss();
            });
            builder.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

    }

    private void showAlertDialog(String title, String[] detailData, Button button) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(detailData, (dialogInterface, i) -> {
            if (detailData[i].equals("Cerrado")) {
                button.setClickable(false);
                binding.detailPriority.setClickable(false);
                // POST CLOSE DATE
            }
            button.setText(detailData[i]);
            setButtonColor(detailData[i], button);
            // POST ACTIONS


            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void showTechnicianAlertDialog(List<Technician> technicianReduced) {
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