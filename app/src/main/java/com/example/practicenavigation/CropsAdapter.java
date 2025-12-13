package com.example.practicenavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CropsAdapter extends RecyclerView.Adapter<CropsAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> list;

    public CropsAdapter(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel product = list.get(position);

        holder.tvName.setText(product.getName());
        holder.tvLocation.setText(product.getLocation());
        holder.tvPrice.setText("Rs. " + product.getPrice());
        holder.tvQuantity.setText(product.getQuantity() + " " + product.getUnit());
        holder.tvDescription.setText(product.getDescription());



        Glide.with(context)
                .load(product.getImageUrl())
                .into(holder.ivProductImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvName, tvLocation, tvPrice, tvDescription, tvQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}
