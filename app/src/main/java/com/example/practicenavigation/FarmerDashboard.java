package com.example.practicenavigation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class FarmerDashboard extends AppCompatActivity {

    TextView tvWelcome;
    Button btnLogout, btnManageCrops, btnOrders, btnProfile;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogout = findViewById(R.id.btnLogout);
        btnProfile = findViewById(R.id.btnProfile);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadFarmerData();

        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(FarmerDashboard.this, ProfileEdit.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> logoutUser());
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(FarmerDashboard.this, PostProduct.class);
            startActivity(intent);
        });



    }

    private void loadFarmerData() {
        String uid = mAuth.getCurrentUser().getUid();
        db.collection("Users").document(uid).get()
                .addOnSuccessListener(snapshot -> {
                    if(snapshot.exists()) {
                        String name = snapshot.getString("name");
                        tvWelcome.setText("Welcome, " + name + "!");
                    } else {
                        tvWelcome.setText("Welcome, Farmer!");
                    }
                })
                .addOnFailureListener(e -> tvWelcome.setText("Welcome, Farmer!"));
    }

    private void logoutUser() {
        mAuth.signOut();
        startActivity(new Intent(FarmerDashboard.this, Splash2.class));
        finish();
    }
}
