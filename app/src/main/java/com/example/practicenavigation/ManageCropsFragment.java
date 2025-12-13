package com.example.practicenavigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ManageCropsFragment extends Fragment {

    private RecyclerView rvCrops;
    private FirebaseFirestore firestore;
    private ArrayList<ProductModel> list;
    private CropsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_crops, container, false);

        rvCrops = view.findViewById(R.id.rvCrops);
        rvCrops.setLayoutManager(new LinearLayoutManager(getContext()));

        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new CropsAdapter(getContext(), list);

        rvCrops.setAdapter(adapter);

        loadProducts();

        return view;
    }

    private void loadProducts() {
        firestore.collection("Products")
                .orderBy("timestamp")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    list.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        ProductModel product = doc.toObject(ProductModel.class);
                        list.add(product);
                    }
                    adapter.notifyDataSetChanged();
                });
    }

}
