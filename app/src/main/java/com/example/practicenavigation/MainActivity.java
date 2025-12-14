package com.example.practicenavigation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.practicenavigation.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home as the default fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        // Handle navigation item clicks
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;

            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
                showActionBar();
            }
//            else if (item.getItemId() == R.id.nav_categories) {
//                selectedFragment = new CategoryFragment();
//                hideActionBar();
//            }
            else if (item.getItemId() == R.id.nav_search) {
                selectedFragment = new SearchFragment();
                showActionBar();
            } else if (item.getItemId() == R.id.nav_cart) {
                selectedFragment = new CartFragment();
                showActionBar();
            } else {
                selectedFragment = new ProfileFragment();
                showActionBar();
            }

            loadFragment(selectedFragment);
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void showActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
        }
    }
}
