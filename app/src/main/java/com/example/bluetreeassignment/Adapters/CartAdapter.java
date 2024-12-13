package com.example.bluetreeassignment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bluetreeassignment.Activities.CartActivity;
import com.example.bluetreeassignment.Models.Product;
import com.example.bluetreeassignment.R;
import com.example.bluetreeassignment.utils.SQLiteHelper;

import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<Product> cartProducts;
    private Map<Integer, Integer> productQuantities;
    private SQLiteHelper sqLiteHelper;

    public CartAdapter(Context context, List<Product> cartProducts, Map<Integer, Integer> productQuantities, SQLiteHelper sqLiteHelper) {
        this.context = context;
        this.cartProducts = cartProducts;
        this.productQuantities = productQuantities;
        this.sqLiteHelper = sqLiteHelper;
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

        // Bind product data to the views
        holder.title.setText(product.getTitle());
        holder.price.setText("$" + product.getPrice());

        int quantity = productQuantities.getOrDefault(product.getId(), 1); // Default quantity to 1
        holder.quantity.setText("Quantity: " + quantity);

        // Load product image using Glide
        Glide.with(context)
                .load(product.getThumbnail() != null ? product.getThumbnail() : R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.productImage);

        // Handle delete button click
        holder.deleteButton.setOnClickListener(v -> {
            boolean isDeleted = sqLiteHelper.removeProductFromCart(product.getId());
            if (isDeleted) {
                // Remove product from the list and notify the adapter
                cartProducts.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartProducts.size());

                if (context instanceof CartActivity) {
                    ((CartActivity) context).updateTotalAmount();
                }

                Toast.makeText(context, "Product removed from cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to remove product", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView title, price, quantity;
        ImageView productImage;
        ImageView deleteButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.productTitlecart);
            price = itemView.findViewById(R.id.productPricecart);
            quantity = itemView.findViewById(R.id.productQuantitycart);
            productImage = itemView.findViewById(R.id.productImagecart);
            deleteButton = itemView.findViewById(R.id.deleteProduct);
        }
    }
}