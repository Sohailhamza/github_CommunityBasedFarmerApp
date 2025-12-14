package com.example.practicenavigation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.practicenavigation.managecrop.ProductModel;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> list;

    public RecommendedAdapter(Context context, List<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.recommended_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductModel product = list.get(position);

        holder.tvName.setText(product.getName());

        Glide.with(context)
                .load(product.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);

        // 🔥 CLICK HANDLER
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetails.class);
            intent.putExtra("farmerId", product.getFarmerId());
            intent.putExtra("name", product.getName());
            intent.putExtra("imageUrl", product.getImageUrl());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("quantity", product.getQuantity());
            intent.putExtra("unit", product.getUnit());
            intent.putExtra("description", product.getDescription());
            intent.putExtra("location", product.getLocation());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recommended_image);
            tvName = itemView.findViewById(R.id.recommended_name);
        }
    }
}
