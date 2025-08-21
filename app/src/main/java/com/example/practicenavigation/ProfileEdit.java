package com.example.practicenavigation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        // Find the back arrow ImageView
        ImageView backArrow = findViewById(R.id.imageView2);

        // Set a click listener for the back arrow
        backArrow.setOnClickListener(v -> {
            // Navigate back to the previous screen (ProfileFragment)
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // This will handle the back navigation
        finish(); // Close the activity and return to the previous screen
    }
}
