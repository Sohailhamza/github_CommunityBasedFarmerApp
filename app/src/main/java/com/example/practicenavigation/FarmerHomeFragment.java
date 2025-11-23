package com.example.practicenavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class FarmerHomeFragment extends Fragment {

    private Button btnManageCrops, btnOrders, btnProfile, btnLogout;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_farmer_home, container, false);
        mAuth = FirebaseAuth.getInstance();

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

    private void logoutUser() {
        mAuth.signOut();
        startActivity(new android.content.Intent(getActivity(), Splash2.class));
        getActivity().finish();
    }
}
