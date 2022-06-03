package com.example.onfieldtbs_android.ui.views;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.onfieldtbs_android.LoginActivity;
import com.example.onfieldtbs_android.R;
import com.example.onfieldtbs_android.databinding.FragmentProfileBinding;
import com.example.onfieldtbs_android.models.Technician;
import com.example.onfieldtbs_android.service.api.Login;
import com.example.onfieldtbs_android.service.api.ApiClient;
import com.example.onfieldtbs_android.service.api.RetrofitCallBack;
import com.example.onfieldtbs_android.service.firebase.FirebaseSingleton;
import com.example.onfieldtbs_android.ui.viewModels.IncidencesViewModel;
import com.example.onfieldtbs_android.ui.views.components.IncidenceTableFragment;
import com.example.onfieldtbs_android.utils.Strings;
import com.example.onfieldtbs_android.utils.Utils;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


public class ProfileFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private FragmentProfileBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApiClient.getApi().getTechnicianByUsername(Login.getInstance().getUsername()).enqueue((RetrofitCallBack<Technician>) (call, response) -> {
            Technician loggedTechnician = response.body();
            String fullName = loggedTechnician.getName() + " " + loggedTechnician.getLastname();
            String fullUsername = "@" + loggedTechnician.getUser().getUsername();
            binding.profileFullName.setText(fullName);
            binding.profileLevel.setText(loggedTechnician.getLevel().getName());
            binding.profileUsername.setText(fullUsername);
            binding.profileOnboarding.setText(loggedTechnician.getCreatedAt());
            binding.profileEmail.setText(loggedTechnician.getEmail());
            binding.profilePhone.setText(loggedTechnician.getPhone());

            // Profile Image
            FirebaseSingleton.getReference("profile-images/profile_" + Login.getInstance().getUsername()).getDownloadUrl()
                    .addOnSuccessListener(uri -> Glide.with(getContext()).load(uri).into(binding.profileImage));

            // View Model
            final IncidencesViewModel incidencesViewModel = new ViewModelProvider(requireActivity()).get(IncidencesViewModel.class);

            // Set technician table data
            incidencesViewModel.getLiveInfo().observe(getViewLifecycleOwner(), liveInfo -> {
                IncidenceTableFragment tableFragment = new IncidenceTableFragment(liveInfo.userIncidences);
                getChildFragmentManager().beginTransaction().replace(R.id.profileTableFragment, tableFragment).commit();
            });
        });

        // Profile Image
        binding.profileImage.setOnClickListener(viewImage -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        // Logout button
        binding.profileLogout.setOnClickListener(viewLogout -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Seguro que quieres cerrar sesion ?");
            builder.setPositiveButton("Si", ((dialogInterface, i) -> {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Strings.PREFERENCES_FILE, Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("isLogged", false).apply();
                getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }));
            builder.setNegativeButton("No", ((dialogInterface, i) -> dialogInterface.dismiss()));
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = intent.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = FirebaseSingleton.getReference("profile-images/profile_" + Login.getInstance().getUsername()).putBytes(data);
            uploadTask.addOnFailureListener(e -> Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show())
                    .addOnSuccessListener(taskSnapshot -> Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show());
            binding.profileImage.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}