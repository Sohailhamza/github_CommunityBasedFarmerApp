package com.example.practicenavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find the Edit Profile button
        Button btnEditProfile = view.findViewById(R.id.btnEditProfile);

        // Set an onClickListener
        btnEditProfile.setOnClickListener(v -> {
            // Open the EditProfileActivity
            Intent intent = new Intent(getActivity(), ProfileEdit.class);
            startActivity(intent);
        });

        return view;
    }
}



