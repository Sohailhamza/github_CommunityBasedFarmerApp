package com.example.practicenavigation.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.practicenavigation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileEdit extends AppCompatActivity {

    private EditText edtName, edtPhone, edtCity;
    private Button btnUpdate;
    private ImageView backArrow;
    private CircleImageView profileImage;
    private TextView tvChangeImg;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private FirebaseUser user;

    private Uri imageUri;

    private static final int PICK_IMAGE = 100;

    // 🔹 Cloudinary (SAFE)
    private static final String CLOUD_NAME = "doaqypmvy";
    private static final String UPLOAD_PRESET = "android_profile_upload"; // <-- CHANGE THIS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        // UI
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtCity = findViewById(R.id.edtCity);
        btnUpdate = findViewById(R.id.btnUpdate);
        backArrow = findViewById(R.id.imageView2);
        profileImage = findViewById(R.id.profile_image);
        tvChangeImg = findViewById(R.id.tvChangeImg);

        // Firebase
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();

        loadUserData();

        backArrow.setOnClickListener(v -> onBackPressed());
        btnUpdate.setOnClickListener(v -> updateProfile());
        tvChangeImg.setOnClickListener(v -> openGallery());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(profileImage);
            uploadImageToCloudinary();
        }
    }

    private void uploadImageToCloudinary() {
        if (imageUri == null || user == null) return;

        String url = "https://api.cloudinary.com/v1_1/" + CLOUD_NAME + "/image/upload";

        try (InputStream inputStream = getContentResolver().openInputStream(imageUri)) {

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int nRead;

            while ((nRead = inputStream.read(data)) != -1) {
                buffer.write(data, 0, nRead);
            }

            byte[] imageBytes = buffer.toByteArray();

            RequestBody fileBody =
                    RequestBody.create(imageBytes, MediaType.parse("image/*"));

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", "profile.jpg", fileBody)
                    .addFormDataPart("upload_preset", UPLOAD_PRESET)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            new OkHttpClient().newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() ->
                            Toast.makeText(ProfileEdit.this,
                                    "Image upload failed", Toast.LENGTH_SHORT).show()
                    );
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        String imageUrl = json.getString("secure_url");
                        saveImageUrlToFirebase(imageUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void saveImageUrlToFirebase(String imageUrl) {
        firestore.collection("Users")
                .document(user.getUid())
                .update("profileImageUrl", imageUrl)
                .addOnSuccessListener(unused ->
                        runOnUiThread(() ->
                                Toast.makeText(this,
                                        "Profile picture updated successfully!",
                                        Toast.LENGTH_SHORT).show()
                        )
                )
                .addOnFailureListener(e ->
                        runOnUiThread(() ->
                                Toast.makeText(this,
                                        "Failed to save URL: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show()
                        )
                );
    }


    private void loadUserData() {
        if (user == null) return;

        firestore.collection("Users")
                .document(user.getUid())
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        edtName.setText(doc.getString("name"));
                        edtPhone.setText(doc.getString("phone"));
                        edtCity.setText(doc.getString("city"));

                        String img = doc.getString("imageUrl");
                        if (img != null && !img.isEmpty()) {
                            Glide.with(this).load(img).into(profileImage);
                        }
                    }
                });
    }

    private void updateProfile() {
        if (user == null) return;

        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String city = edtCity.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || city.isEmpty()) {
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            return;
        }

        firestore.collection("Users")
                .document(user.getUid())
                .update(
                        "name", name,
                        "phone", phone,
                        "city", city
                )
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }
}
