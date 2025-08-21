package com.example.practicenavigation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        TextView toLogin = (TextView) findViewById(R.id.toSignUp);
        toLogin.setOnClickListener(v -> {

            // Navigate to Sign Up activity
        });

        Button LoginButton = (Button) findViewById(R.id.Loginbutton);
        LoginButton.setOnClickListener(view -> {
            // Perform your login logic here. For simplicity, we'll just finish the activity
            Intent intent = new Intent(LogIn.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        TextView toSignUp = (TextView) findViewById(R.id.toSignUp);
        toSignUp.setOnClickListener(view -> {
            Intent intent =new Intent(LogIn.this, SignUp.class);
            startActivity(intent);
        });

    }
}