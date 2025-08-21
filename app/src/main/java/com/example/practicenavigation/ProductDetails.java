package com.example.practicenavigation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetails extends AppCompatActivity {

    private ImageView imageView;
    private TextView nameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details); // Ensure this matches your XML filename

        // Initialize views - Ensure these IDs exist in activity_product_details.xml
        imageView = findViewById(R.id.product_image);      // Should exist in your XML
        nameTextView = findViewById(R.id.product_name);    // Should exist in your XML
        Button contact_seller_button = findViewById(R.id.contact_seller_button);

        // Receive intent data from adapter
        Intent intent = getIntent();
        if (intent != null) {
            String productName = intent.getStringExtra("product_name");
            int productImage = intent.getIntExtra("product_image", R.drawable.soyabeen); // fallback image

            // Set the product data to views
            nameTextView.setText(productName != null ? productName : "Unknown Product");
            imageView.setImageResource(productImage);
        }

        // Button click to open ContactSeller activity
        contact_seller_button.setOnClickListener(v -> {
            Intent i = new Intent(ProductDetails.this, ContactSeller.class);
            startActivity(i);
        });
    }
}
