package com.example.practicenavigation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class PostProduct extends AppCompatActivity {
    private Insets insets;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_product);

        Button btnPost = findViewById(R.id.btnPost);
        ImageView btnBack = findViewById(R.id.ivBack);

        btnPost.setOnClickListener(view -> {
            Intent intent = new Intent(PostProduct.this, FarmerDashboard.class);

            Toast.makeText(PostProduct.this, "Post Successful!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(PostProduct.this, FarmerDashboard.class);
            startActivity(intent);
            finish();
        });
    }
    }
