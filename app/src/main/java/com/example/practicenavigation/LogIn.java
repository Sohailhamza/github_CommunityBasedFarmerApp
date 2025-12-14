package com.example.practicenavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogIn extends AppCompatActivity {

    TextInputEditText edtEmailLogin, edtPasswordLogin;
    Button btnLogin;
    TextView toSignUp;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_log_in);

        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.Loginbutton);
        toSignUp = findViewById(R.id.toSignUp);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        toSignUp.setOnClickListener(v ->
                startActivity(new Intent(LogIn.this, SignUp.class)));

        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = edtEmailLogin.getText().toString().trim();
        String password = edtPasswordLogin.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email & Password required!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    String uid = mAuth.getCurrentUser().getUid();
                    db.collection("Users").document(uid).get()
                            .addOnSuccessListener(snapshot -> {
                                if(snapshot.exists()) {
                                    String role = snapshot.getString("role");

                                    if("Consumer".equals(role)) {
                                        startActivity(new Intent(LogIn.this, MainActivity.class));
                                    } else if("Farmer".equals(role)) {
                                        startActivity(new Intent(LogIn.this, FarmerDashboard.class));
                                    } else {
                                        Toast.makeText(this, "Unknown user role!", Toast.LENGTH_SHORT).show();
                                    }
                                    finish();
                                } else {
                                    Toast.makeText(this, "User details not found!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "Firestore error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
