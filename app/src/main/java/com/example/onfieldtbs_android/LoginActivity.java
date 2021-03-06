package com.example.onfieldtbs_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onfieldtbs_android.service.api.Login;
import com.example.onfieldtbs_android.service.api.LoginService;
import com.example.onfieldtbs_android.databinding.ActivityLoginBinding;
import com.example.onfieldtbs_android.utils.Strings;
import com.example.onfieldtbs_android.utils.Utils;

import java.nio.charset.StandardCharsets;
import android.util.Base64;

public class LoginActivity extends AppCompatActivity {

    private static final String ERR_EMPTY = "Error, campos vacíos";
    private static final String ERR_PASS = "Error, la contraseña es demasiado corta";
    private static final int MIN_LENGTH = 6;

    private ActivityLoginBinding binding;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set initial text
        binding.loginUsernameEditText.setText("");
        binding.loginPasswordEditText.setText("");

        // Button Listener
        binding.loginButton.setOnClickListener(view -> {


            // Check if empty
            if (binding.loginUsernameEditText.getText().toString().matches("") || binding.loginPasswordEditText.getText().toString().matches("")){
                makeToast(ERR_EMPTY);
                return;
            }

            // Get email and password
            username = binding.loginUsernameEditText.getText().toString();
            password = binding.loginPasswordEditText.getText().toString();

            // Check password length
            if(password.length() < MIN_LENGTH){
                makeToast(ERR_PASS);
                return;
            }


            
            // Go to Main Activity
            LoginService service = new LoginService(getApplicationContext());

            service.login(username, password, response -> {
                if (response) {
                    SharedPreferences.Editor editor = getSharedPreferences(Strings.PREFERENCES_FILE, MODE_PRIVATE).edit();
                    editor.putBoolean("isLogged", true);
                    editor.putString("username", Login.getInstance().getUsername());
                    editor.putString("auth", Login.getAuth());
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });

        });
    }

    private void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}