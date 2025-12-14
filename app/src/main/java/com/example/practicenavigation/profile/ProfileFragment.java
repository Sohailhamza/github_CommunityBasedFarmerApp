package com.example.practicenavigation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.practicenavigation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private CircleImageView profileImage;
    private TextView tvName;
    private Button btnEditProfile;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = view.findViewById(R.id.profile_image);
        tvName = view.findViewById(R.id.tvName);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();

        // Load profile data
        loadUserProfile();

        btnEditProfile.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), ProfileEdit.class))
        );

        return view;
    }

    private void loadUserProfile() {
        if (user == null) return;

        firestore.collection("Users")
                .document(user.getUid())
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        String name = doc.getString("name");
                        String profileImageUrl = doc.getString("profileImageUrl");

                        tvName.setText(name != null ? name : "Your Name");

                        // Load profile image using Glide
                        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                            Glide.with(this)  // use 'this' Fragment for Glide
                                    .load(profileImageUrl)
                                    .placeholder(R.drawable.by_default_image_view) // default image if empty
                                    .error(R.drawable.by_default_image_view)       // if loading fails
                                    .circleCrop()                   // make it circular
                                    .into(profileImage);
                        } else {
                            // If no URL, show default
                            profileImage.setImageResource(R.drawable.sohail);
                        }
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload profile in case user updated image in ProfileEdit
        loadUserProfile();
    }
}
