package com.example.practicenavigation;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView categoryRecyclerView, recommendedRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecommendedAdapter recommendedAdapter;
    private List<Category> categoryList;
    private List<RecommendedItem> recommendedList;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);


        // Initialize RecyclerViews
        categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        recommendedRecyclerView = view.findViewById(R.id.recommended_recycler_view);

        // Setup Category RecyclerView with horizontal LinearLayoutManager
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryList = new ArrayList<>();
        categoryList.add(new Category("Green", R.drawable.greem));
        categoryList.add(new Category("Almond", R.drawable.almond));
        categoryList.add(new Category("Wheat", R.drawable.wheet));
        categoryList.add(new Category("Dry Fruits", R.drawable.dryfruits));
        categoryList.add(new Category("Wheat", R.drawable.wheet));
        categoryList.add(new Category("Wheat", R.drawable.wheet));
        categoryAdapter = new CategoryAdapter(categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);

        // Setup Recommended RecyclerView with GridLayoutManager (2 columns)
        recommendedRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        int spacingInPixels = 16; // Adjust spacing as needed
        int numberOfColumns = 2;
        recommendedRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spacingInPixels, numberOfColumns));

        recommendedList = new ArrayList<>();
        recommendedList.add(new RecommendedItem("Soyabeen", R.drawable.soyabeen));
        recommendedList.add(new RecommendedItem("Fig Regular", R.drawable.figreular));
        recommendedList.add(new RecommendedItem("Almonds", R.drawable.almond));
        recommendedList.add(new RecommendedItem("Dry Fruits", R.drawable.dryfruits));
        recommendedList.add(new RecommendedItem("Soybean", R.drawable.soyabeen));
        recommendedList.add(new RecommendedItem("Fig Regular", R.drawable.figreular));
        recommendedList.add(new RecommendedItem("Almonds", R.drawable.almond));
        recommendedList.add(new RecommendedItem("Almonds", R.drawable.almond));

        recommendedAdapter = new RecommendedAdapter(requireContext(), recommendedList);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

        // Handle click on "Shop by category" TextView to navigate to CategoryFragment
        TextView toShopTextView = view.findViewById(R.id.ShopByCat);
        toShopTextView.setOnClickListener(v -> {
            CategoryFragment categoriesFragment = new CategoryFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, categoriesFragment) // Your fragment container id here
                    .addToBackStack(null) // Add to back stack for back navigation
                    .commit();
        });
//        FloatingActionButton fab = view.findViewById(R.id.fab);
//        fab.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), PostProduct.class);
//            startActivity(intent);
//        });

    }
}
