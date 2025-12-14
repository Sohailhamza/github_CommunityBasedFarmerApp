package com.example.practicenavigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicenavigation.managecrop.ProductModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recommendedRecyclerView;
    private RecommendedAdapter recommendedAdapter;
    private FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recommendedRecyclerView = view.findViewById(R.id.recommended_recycler_view);
        recommendedRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        // Initialize adapter (no data yet)
        recommendedAdapter = new RecommendedAdapter(requireContext());
        recommendedRecyclerView.setAdapter(recommendedAdapter);

        firestore = FirebaseFirestore.getInstance();

        // Load Firestore data
        loadRecommendedProducts();

        // Setup SearchView
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recommendedAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recommendedAdapter.filter(newText);
                return false;
            }
        });
    }

    private void loadRecommendedProducts() {
        firestore.collection("Products")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<ProductModel> productList = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        ProductModel product = doc.toObject(ProductModel.class);
                        productList.add(product);
                    }
                    recommendedAdapter.updateList(productList);
                    Log.d("HOME", "Products loaded: " + productList.size());
                })
                .addOnFailureListener(e -> Log.e("HOME", "Error loading products", e));
    }
}
