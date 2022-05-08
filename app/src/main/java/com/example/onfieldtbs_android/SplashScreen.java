package com.example.onfieldtbs_android;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onfieldtbs_android.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 5000;

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

        // Go to login
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        },SPLASH_SCREEN);

    }
}