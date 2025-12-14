package com.example.practicenavigation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Splash2 extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 5000; // 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Disable night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash2);

        ImageView nextArrow = findViewById(R.id.nextArrow);
        nextArrow.setOnClickListener(v -> {
            // Move to login screen when arrow clicked
            openLoginActivity();
        });

        // Automatic transition after SPLASH_TIME_OUT milliseconds
        new Handler().postDelayed(this::openLoginActivity, SPLASH_TIME_OUT);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(Splash2.this, LogIn.class);
        startActivity(intent);
        finish(); // Close splash screen so user cannot go back
    }
}
