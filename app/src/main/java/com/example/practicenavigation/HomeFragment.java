package com.example.practicenavigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicenavigation.managecrop.ProductModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import com.example.practicenavigation.managecrop.ProductModel;


public class HomeFragment extends Fragment {

    private RecyclerView recommendedRecyclerView;
    private RecommendedAdapter recommendedAdapter;
    private List<ProductModel> productList;
    private FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recommendedRecyclerView = view.findViewById(R.id.recommended_recycler_view);
        recommendedRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        productList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(requireContext(), productList);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

        firestore = FirebaseFirestore.getInstance();

        loadRecommendedProducts();
    }

    private void loadRecommendedProducts() {

        firestore.collection("Products")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    Log.d("HOME", "Docs size: " + queryDocumentSnapshots.size());

                    productList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        ProductModel product = doc.toObject(ProductModel.class);
                        productList.add(product);
                    }
                    recommendedAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Log.e("HOME", "Error", e));
    }
}
