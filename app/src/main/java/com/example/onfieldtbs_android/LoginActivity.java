package com.example.onfieldtbs_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onfieldtbs_android.service.api.LoginService;
import com.example.onfieldtbs_android.databinding.ActivityLoginBinding;
import com.example.onfieldtbs_android.service.api.Model.ApiClient;
import com.example.onfieldtbs_android.service.api.Model.RetrofitCallBack;

import retrofit2.Call;
import retrofit2.Response;

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


        // TESTING ################################################################
        binding.btnGarcia.setOnClickListener(view -> {
            binding.loginUsernameEditText.setText("jgarcia");
            binding.loginPasswordEditText.setText("Jg@rc1A");
        });
        binding.btnLopez.setOnClickListener(view -> {
            binding.loginUsernameEditText.setText("nlopez");
            binding.loginPasswordEditText.setText("Nl0p3Z");
        });
        // TESTING ################################################################

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
            // TODO(Retrofit)
             LoginService service = new LoginService(getApplicationContext());
             service.login(username, password, response -> {
                if (response) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        });
    }

    private void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}