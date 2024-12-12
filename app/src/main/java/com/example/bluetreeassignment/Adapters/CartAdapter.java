package com.example.bluetreeassignment.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bluetreeassignment.Models.Product;
import com.example.bluetreeassignment.R;

import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> cartProducts;
    private Map<Integer, Integer> productQuantities;


    public CartAdapter(List<Product> cartProducts,Map<Integer,Integer> productQuantities) {
        this.cartProducts = cartProducts;
        this.productQuantities=productQuantities;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartProducts.get(position);
        holder.title.setText(product.getTitle());
        holder.price.setText("$" + product.getPrice());

        int quantity = productQuantities.getOrDefault(product.getId(), 1); // Default to 1 if not found
        holder.quantity.setText("Quantity: " + quantity);

        Glide.with(holder.productImage.getContext())
                .load(product.getThumbnail() != null ? product.getThumbnail() : R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView title, price, quantity;
        ImageView productImage;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.productTitlecart);
            price = itemView.findViewById(R.id.productPricecart);
            quantity = itemView.findViewById(R.id.productQuantitycart);
            productImage=itemView.findViewById(R.id.productImagecart);
        }
    }
}