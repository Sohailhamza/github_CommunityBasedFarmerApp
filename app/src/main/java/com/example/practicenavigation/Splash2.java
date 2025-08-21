package com.example.practicenavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash2);


        ImageView nextArrow = findViewById(R.id.nextArrow);
        nextArrow.setOnClickListener(v -> {
            Intent intent = new Intent(Splash2.this, LogIn.class);
            startActivity(intent);
        });
    }
}