package com.example.practicenavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.practicenavigation.managecrop.ManageCropsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FarmerHomeFragment extends Fragment {

    private Button btnManageCrops, btnOrders, btnProfile, btnLogout;

    TextView tvWelcome;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_farmer_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tvWelcome = view.findViewById(R.id.tvWelcome);
        loadFarmerData();


        btnManageCrops = view.findViewById(R.id.btnManageCrops);
        btnOrders = view.findViewById(R.id.btnOrders);
        btnProfile = view.findViewById(R.id.btnProfile);
        btnLogout = view.findViewById(R.id.btnLogout);

        btnManageCrops.setOnClickListener(v -> switchFragment(R.id.nav_crops));
        btnOrders.setOnClickListener(v -> switchFragment(R.id.nav_orders));
        btnProfile.setOnClickListener(v -> switchFragment(R.id.nav_profile));
        btnLogout.setOnClickListener(v -> logoutUser());

        return view;
    }

    private void switchFragment(int navItemId) {
        // Find BottomNavigationView in parent activity
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);

        // Update selected item
        bottomNav.setSelectedItemId(navItemId);

        // Replace fragment manually (optional, in case listener doesn't trigger)
        Fragment fragment = null;
        if (navItemId == R.id.nav_crops) fragment = new ManageCropsFragment();
        else if (navItemId == R.id.nav_orders) fragment = new OrdersFragment();
        else if (navItemId == R.id.nav_profile) fragment = new ProfileFragment();

        if (fragment != null) {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
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
        startActivity(new android.content.Intent(getActivity(), Splash2.class));
        getActivity().finish();
    }
}
