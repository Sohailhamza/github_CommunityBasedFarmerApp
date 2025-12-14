package com.example.practicenavigation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductDetails extends AppCompatActivity {

    private ImageView imageView;
    private TextView nameTextView, priceTextView, descTextView, locationTextView;

    private FirebaseFirestore firestore;
    private ImageView farmerImage;
    private TextView farmerName, farmerCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        imageView = findViewById(R.id.product_image);
        nameTextView = findViewById(R.id.product_name);
        priceTextView = findViewById(R.id.product_price);
        descTextView = findViewById(R.id.product_description);
        locationTextView = findViewById(R.id.product_location);

        farmerImage = findViewById(R.id.farmer_profile_image);
        farmerName = findViewById(R.id.farmer_name);
        farmerCity = findViewById(R.id.farmer_city);

        firestore = FirebaseFirestore.getInstance();

        Button contactSellerBtn = findViewById(R.id.contact_seller_button);

        Intent intent = getIntent();

        if (intent != null) {
            // Retrieve data from the intent
            String farmerId = intent.getStringExtra("farmerId");

            if (farmerId != null) {
                firestore.collection("Users")
                        .document(farmerId)
                        .get()
                        .addOnSuccessListener(doc -> {
                            if (doc.exists()) {

                                String fName = doc.getString("name");
                                String fCity = doc.getString("city");
                                String profileUrl = doc.getString("profileImageUrl");

                                farmerName.setText(fName);
                                farmerCity.setText(fCity);

                                if (profileUrl != null && !profileUrl.isEmpty()) {
                                    Glide.with(ProductDetails.this)
                                            .load(profileUrl)
                                            .placeholder(R.drawable.placeholder)
                                            .error(R.drawable.placeholder)
                                            .into(farmerImage);
                                } else {
                                    farmerImage.setImageResource(R.drawable.placeholder);
                                }

                            }
                        });
            }

            String name = intent.getStringExtra("name");
            String imageUrl = intent.getStringExtra("imageUrl");
            String price = intent.getStringExtra("price");
            String quantity = intent.getStringExtra("quantity");
            String unit = intent.getStringExtra("unit");
            String description = intent.getStringExtra("description");
            String location = intent.getStringExtra("location");
            // Set the data to the views

            nameTextView.setText(name);
            priceTextView.setText(price + " Rs " + " Per " + quantity + " " + unit);

            descTextView.setText(description);
            locationTextView.setText(location);

            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);
        }

        contactSellerBtn.setOnClickListener(v -> {
            startActivity(new Intent(ProductDetails.this, ContactSeller.class));
        });
    }
}
