package com.example.practicenavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.practicenavigation.managecrop.ManageCropsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


public class FarmerDashboard extends AppCompatActivity {

    FloatingActionButton fab;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        mAuth = FirebaseAuth.getInstance();
        fab = findViewById(R.id.fab);

        // Default fragment on start
        setupBottomNavigation();
        loadFragment(new FarmerHomeFragment());


        fab.setOnClickListener(v ->
                startActivity(new Intent(FarmerDashboard.this, PostProduct.class))
        );
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }



    @SuppressLint("NonConstantResourceId")
    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();
            Fragment selectedFragment = null;

            if (id == R.id.nav_farmer_home) {
                selectedFragment = new FarmerHomeFragment();
            } else if (id == R.id.nav_crops) {
                selectedFragment = new ManageCropsFragment();
            } else if (id == R.id.nav_orders) {
                selectedFragment = new OrdersFragment();
            } else if (id == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            // Load fragment
            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true; // ✅ Important: Must return a boolean
        });

    }

}
