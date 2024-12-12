package com.example.bluetreeassignment.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.bluetreeassignment.Activities.ProductDetailsActivity;
import com.example.bluetreeassignment.Models.Product;
import com.example.bluetreeassignment.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final List<Product> productList;
    private Context context;


    public ProductAdapter(List<Product> productList,Context context) {
        this.productList = productList;
        this.context=context;

    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each product item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Set the title and price of the product
        holder.productTitle.setText(product.getTitle());
        holder.productPrice.setText("$" + product.getPrice());

        // Use Glide to load the product image into the ImageView
        Glide.with(holder.productImage.getContext())
                .load(product.getThumbnail() != null ? product.getThumbnail() : R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.productImage);


        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// Passing the product ID
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder class to hold references to the product views
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productTitle, productPrice;
        ImageView productImage;
        CardView cardView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productTitle = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}