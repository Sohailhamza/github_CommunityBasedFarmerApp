package com.example.practicenavigation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class FarmerDashboard extends AppCompatActivity {

    TextView tvWelcome;
    Button btnLogout;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogout = findViewById(R.id.btnLogout);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadFarmerData();

        btnLogout.setOnClickListener(v -> logoutUser());
    }

    private void loadFarmerData() {
        String uid = mAuth.getCurrentUser().getUid();
        db.collection("Users").document(uid).get()
                .addOnSuccessListener(snapshot -> {
                    if(snapshot.exists()) {
                        String name = snapshot.getString("name");
                        tvWelcome.setText("Welcome, Farmer " + name + "!");
                    } else {
                        tvWelcome.setText("Welcome, Farmer!");
                    }
                })
                .addOnFailureListener(e -> tvWelcome.setText("Welcome, Farmer!"));
    }

    private void logoutUser() {
        mAuth.signOut();
        startActivity(new Intent(FarmerDashboard.this, LogIn.class));
        finish();
    }
}
