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

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {

    private final List<RecommendedItem> recommendedList;
    private final Context context;

    // ✅ Proper constructor with context
    public RecommendedAdapter(Context context, List<RecommendedItem> recommendedList) {
        this.context = context;
        this.recommendedList = recommendedList;
    }

    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item, parent, false);
        return new RecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, int position) {
        RecommendedItem item = recommendedList.get(position);
        holder.recommendedName.setText(item.getName());
        holder.recommendedImage.setImageResource(item.getImageResource());

        // ✅ Start ProductDetails activity on click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetails.class);
            intent.putExtra("product_name", item.getName());
            intent.putExtra("product_image", item.getImageResource());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    public static class RecommendedViewHolder extends RecyclerView.ViewHolder {

        ImageView recommendedImage;
        TextView recommendedName;

        public RecommendedViewHolder(View itemView) {
            super(itemView);
            recommendedImage = itemView.findViewById(R.id.recommended_image);
            recommendedName = itemView.findViewById(R.id.recommended_name);
        }
    }
}
