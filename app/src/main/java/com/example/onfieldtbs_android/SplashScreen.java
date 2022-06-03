package com.example.onfieldtbs_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.onfieldtbs_android.databinding.ActivitySplashScreenBinding;
import com.example.onfieldtbs_android.service.api.Login;
import com.example.onfieldtbs_android.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 5000;
    private static final int FRAME_DURATION = 100;

    private ActivitySplashScreenBinding binding;
    private ObjectAnimator animationScaleX;
    private ObjectAnimator animationScaleY;
    private ObjectAnimator animationScaleXReduce;
    private ObjectAnimator animationScaleYReduce;
    private AnimatorSet animatorTitleStart = new AnimatorSet();
    private AnimatorSet animatorTitleReduce = new AnimatorSet();
    private AnimatorSet animatorTitle = new AnimatorSet();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Logo Animation
        AnimationDrawable animationLogo = new AnimationDrawable();
        addFrames(animationLogo);
        binding.splashLogo.setImageDrawable(animationLogo);
        animationLogo.start();

        // Set title scale to minimum
        binding.splashTitle.setScaleX(0);
        binding.splashTitle.setScaleY(0);

        // Scale to oversize animations
        animationScaleX = ObjectAnimator.ofFloat(binding.splashTitle, "scaleX", 1.5f).setDuration(2000);
        animationScaleY = ObjectAnimator.ofFloat(binding.splashTitle, "scaleY", 1.5f).setDuration(2000);

        // Scale to normal size animations
        animationScaleXReduce = ObjectAnimator.ofFloat(binding.splashTitle, "scaleX", 1f).setDuration(1000);
        animationScaleYReduce = ObjectAnimator.ofFloat(binding.splashTitle, "scaleY", 1f).setDuration(1000);

        // Animator sets config
        animatorTitleStart.playTogether(animationScaleX, animationScaleY);
        animatorTitleReduce.playTogether(animationScaleXReduce, animationScaleYReduce);
        animatorTitle.playSequentially(animatorTitleStart, animatorTitleReduce);

        // Animations start
        animatorTitle.start();

        // Logo animation stop
        new Handler().postDelayed(animationLogo::stop, 3000);

        SharedPreferences sharedPreferences = getSharedPreferences(Utils.PREFERENCES_FILE,Context.MODE_PRIVATE);

        // Go to login or main activity
        new Handler().postDelayed(() -> {
            Intent intent;

            if (!sharedPreferences.getBoolean("isLogged", true)) {
                intent = new Intent(this, LoginActivity.class);
            } else {
                Login.initInstanceAuth(sharedPreferences.getString("auth",""));
                intent = new Intent(this, MainActivity.class);
            }
            startActivity(intent);
            this.finish();
        },SPLASH_SCREEN);

    }

    private void addFrames(AnimationDrawable animationLogo) {
        List<Integer> images = new ArrayList<>(Arrays.asList(
                R.drawable.logo_onfield,
                R.drawable.logo_onfield2,
                R.drawable.logo_onfield3,
                R.drawable.logo_onfield4,
                R.drawable.logo_onfield5
                ));
        images.forEach(image -> animationLogo.addFrame(ResourcesCompat.getDrawable(getResources(), image, null), FRAME_DURATION));

    }
}