package com.example.practicenavigation;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.category_recycler_view);

        // 🔁 Grid layout with 2 columns
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Green", R.drawable.greem));
        categoryList.add(new Category("Almond", R.drawable.almond));
        categoryList.add(new Category("Dry Fruits", R.drawable.dryfruits));

        CategoryAdapter adapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
