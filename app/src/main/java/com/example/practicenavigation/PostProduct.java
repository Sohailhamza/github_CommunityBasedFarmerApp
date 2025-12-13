package com.example.practicenavigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PostProduct extends AppCompatActivity {

    private static final int PICK_IMAGE = 101;

    private ImageView ivAddPhoto, ivBack;
    private EditText etProductName, etLocation, etDescription, etPrice;
    private Button btnPost;

    private Uri imageUri;

    private EditText etQuantity;
    private Spinner spUnit;

    private ProgressDialog progressDialog;

    // TODO — Replace with your real Cloudinary keys
    private final String CLOUD_NAME = "doaqypmvy";
    private final String API_KEY = "283699849981897";
    private final String API_SECRET = "iKgxZGkfcTJwHBXSxZ_JBpeqrz8";

    private Cloudinary cloudinary;

    //Cloudinary code init finish




    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_product);

        // Firestore
        firestore = FirebaseFirestore.getInstance();

        // Cloudinary Config
        Map config = ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET
        );
        cloudinary = new Cloudinary(config);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Product...");

        // View Hooks
        ivAddPhoto = findViewById(R.id.ivAddPhoto);
        ivBack = findViewById(R.id.ivBack);
        etProductName = findViewById(R.id.etProductName);
        etLocation = findViewById(R.id.etLocation);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        btnPost = findViewById(R.id.btnPost);

        // Pick Image
        ivAddPhoto.setOnClickListener(v -> pickImage());

        // Quantity + Unit
        etQuantity = findViewById(R.id.etQuantity);
        spUnit = findViewById(R.id.spUnit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.quantity_units,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUnit.setAdapter(adapter);

        //Quantity + Unit end


        // Back Button
        ivBack.setOnClickListener(v -> {
            startActivity(new Intent(PostProduct.this, FarmerDashboard.class));
            finish();
        });

        // Post Product
        btnPost.setOnClickListener(v -> uploadProduct());
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            ivAddPhoto.setImageURI(imageUri);
        }
    }



    private void uploadProduct() {

        String name = etProductName.getText().toString();
        String location = etLocation.getText().toString();
        String description = etDescription.getText().toString();
        String price = etPrice.getText().toString();
        String quantity = etQuantity.getText().toString();
        String unit = spUnit.getSelectedItem().toString();


        if (name.isEmpty() || location.isEmpty() || description.isEmpty() ||
                price.isEmpty() || quantity.isEmpty() || unit.isEmpty() || imageUri == null) {

            Toast.makeText(this, "Fill all fields & choose an image!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        new Thread(() -> {
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);

                Map uploadResult = cloudinary.uploader().upload(inputStream,
                        ObjectUtils.asMap(
                                "folder", "farmer_products",
                                "resource_type", "image"
                        ));

                String imageUrl = uploadResult.get("secure_url").toString();

                HashMap<String, Object> product = new HashMap<>();
                product.put("name", name);
                product.put("location", location);
                product.put("description", description);
                product.put("price", price);
                product.put("quantity", quantity);
                product.put("unit", unit);
                product.put("imageUrl", imageUrl);
                product.put("timestamp", System.currentTimeMillis());

                firestore.collection("Products")
                        .add(product)
                        .addOnSuccessListener(doc -> {
                            progressDialog.dismiss();
                            Toast.makeText(PostProduct.this, "Product Posted!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PostProduct.this, FarmerDashboard.class));
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            progressDialog.dismiss();
                            Toast.makeText(PostProduct.this,
                                    "Firestore Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });

            } catch (Exception e) {
                runOnUiThread(() -> {
                    progressDialog.dismiss();
                    Toast.makeText(PostProduct.this,
                            "Cloudinary Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
            }
        }).start();

    }
}
