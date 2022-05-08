package com.example.onfieldtbs_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onfieldtbs_android.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private static final String ERR_EMPTY = "Error, campos vacíos";
    private static final String ERR_FORMAT = "Error, correo electrónico no válido";
    private static final String ERR_PASS = "Error, la contraseña es demasiado corta";
    private static final String FORMAT_EMAIL = "^[^@].*@oftbs.org$";
    private static final int MIN_LENGTH = 8;

    private ActivityLoginBinding binding;
    private String email;
    private String password;
    private Intent mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set initial text
        binding.loginEmailEditText.setText("");
        binding.loginPasswordEditText.setText("");

        // Button Listener
        binding.loginButton.setOnClickListener(view -> {

            // Check if empty
            if (binding.loginEmailEditText.getText().toString().matches("") || binding.loginPasswordEditText.getText().toString().matches("")){
                makeToast(ERR_EMPTY);
                return;
            }

            // Get email and password
            email = binding.loginEmailEditText.getText().toString();
            password = binding.loginPasswordEditText.getText().toString();

            // Check email format
            if (!email.matches(FORMAT_EMAIL)){
                makeToast(ERR_FORMAT);
                return;
            }

            // Check password length
            if(password.length() < MIN_LENGTH){
                makeToast(ERR_PASS);
                return;
            }

            // TEST
            // Show email and password in Debug Log
            Log.d("LOGIN", "email: " + email + ", password: " + password);

            // Go to Main Activity
            mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);

        });
    }

    private void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}