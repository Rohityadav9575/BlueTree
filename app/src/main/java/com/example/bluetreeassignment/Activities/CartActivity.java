package com.example.bluetreeassignment.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
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

        databaseHelper = SQLiteHelper.getInstance(this);


        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "default_username");

        TextView usernameTextView = findViewById(R.id.username);
        usernameTextView.setText(username);

        loadCartItems();

        ImageButton logoutButton = findViewById(R.id.logout);

        logoutButton.setOnClickListener(v -> {
           logout();
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadCartItems() {

        List<Product> cartProducts = databaseHelper.getCartItems();
        double total = 0;
        Map<Integer, Integer> productQuantities = new HashMap<>();

        if (cartProducts != null && !cartProducts.isEmpty()) {

            for (Product product : cartProducts) {
                int quantity = product.getQuantity();
                productQuantities.put(product.getId(), quantity);
                total += product.getPrice() * quantity;
            }

            cartAdapter = new CartAdapter(this,cartProducts, productQuantities,databaseHelper);
            cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            cartRecyclerView.setAdapter(cartAdapter);


            totalAmount.setText("Total Amount: $" + total);
        } else {

            totalAmount.setText("Total Amount: $0");
        }
    }



    public void updateTotalAmount() {
        double total = databaseHelper.calculateTotalAmount();
        totalAmount.setText("Total Amount: $" + total);
    }



    public void logout(){

        SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
        sqLiteHelper.clearCart();


        Intent intent = new Intent(this, LandingActivity.class); // Replace with your landing page activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

}