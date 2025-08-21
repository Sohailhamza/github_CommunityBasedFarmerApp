package com.example.practicenavigation;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private TextView totalPriceText;
    private Button checkoutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        totalPriceText = view.findViewById(R.id.total_price_value);
        checkoutButton = view.findViewById(R.id.checkout_button);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(new CartItem("Greem", R.drawable.greem, 799));
        items.add(new CartItem("Almond", R.drawable.almond, 1199));
        items.add(new CartItem("Sneakers", R.drawable.dryfruits, 2499));

        CartAdapter adapter = new CartAdapter(items);
        recyclerView.setAdapter(adapter);

        updateTotalPrice(items);

        checkoutButton.setOnClickListener(v -> showConfirmationDialog());

        return view;
    }

    private void updateTotalPrice(ArrayList<CartItem> items) {
        double total = 0;
        for (CartItem item : items) {
            total += item.getPrice();
        }
        totalPriceText.setText("Rs. " + total);
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Checkout")
                .setMessage("Are you sure you want to complete your purchase?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Perform checkout logic here
                    Toast.makeText(getContext(), "Checkout complete! Thanks for shopping.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
