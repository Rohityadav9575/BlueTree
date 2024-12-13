package com.example.bluetreeassignment.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluetreeassignment.Adapters.ProductAdapter;
import com.example.bluetreeassignment.Models.ProductList;
import com.example.bluetreeassignment.Models.Product;
import com.example.bluetreeassignment.Network.ApiClient;
import com.example.bluetreeassignment.Network.ApiService;
import com.example.bluetreeassignment.R;
import com.example.bluetreeassignment.utils.SQLiteHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private TextView user;
    private ImageButton logoutButton,cart;
    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
        sqLiteHelper = SQLiteHelper.getInstance(this);


        initViews();
        setupActionBar();
        setupWindowInsets();
        fetchProducts();
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(v -> {

           logout();
        });
    }

    private void logout() {
        sqLiteHelper = new SQLiteHelper(this);
        sqLiteHelper.clearCart();


        Intent intent = new Intent(this, LandingActivity.class); // Replace with your landing page activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    // Initialize views
    private void initViews() {
        user=findViewById(R.id.username);
        cart=findViewById(R.id.cart);
        logoutButton = findViewById(R.id.logout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void setupActionBar() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Guest");
        user.setText(username);

    }

    // Set up window insets for proper padding handling
    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Fetch products from the API and set up the RecyclerView
    private void fetchProducts() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ProductList> call = apiService.getProducts();

        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList = response.body().getProducts();
                    if (productList != null && !productList.isEmpty()) {
                        productAdapter = new ProductAdapter(productList,getApplicationContext());
                        recyclerView.setAdapter(productAdapter);
                    } else {
                        Log.e("ProductListActivity", "Product list is empty");
                    }
                } else {
                    Log.e("ProductListActivity", "Response error: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                Log.e("ProductListActivity", "Error fetching products", t);
            }
        });
    }
}