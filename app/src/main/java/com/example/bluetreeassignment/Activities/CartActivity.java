package com.example.bluetreeassignment.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetreeassignment.Adapters.CartAdapter;
import com.example.bluetreeassignment.Models.Product;
import com.example.bluetreeassignment.R;
import com.example.bluetreeassignment.utils.SQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private TextView totalAmount;
    private CartAdapter cartAdapter;
    private SQLiteHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalAmount = findViewById(R.id.cartTotalAmount);
        databaseHelper = new SQLiteHelper(this);

        loadCartItems();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadCartItems() {
        // Get the list of products directly from the database helper
        List<Product> cartProducts = databaseHelper.getCartItems();
        double total = 0;
        Map<Integer, Integer> productQuantities = new HashMap<>();  // Map to store quantities

        if (cartProducts != null && !cartProducts.isEmpty()) {
            // Loop through the product list and calculate the total amount
            for (Product product : cartProducts) {
                int quantity = product.getQuantity();  // Assuming quantity is already set on the Product object
                productQuantities.put(product.getId(), quantity);  // Store the quantity in the map
                total += product.getPrice() * quantity;  // Calculate the total price
            }

            // Set up the adapter for RecyclerView with product list and quantity map
            cartAdapter = new CartAdapter(cartProducts, productQuantities);
            cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            cartRecyclerView.setAdapter(cartAdapter);

            // Set the total amount in the TextView
            totalAmount.setText("Total Amount: $" + total);
        } else {
            // Handle the case when there are no products in the cart
            totalAmount.setText("Total Amount: $0");
        }
    }
}