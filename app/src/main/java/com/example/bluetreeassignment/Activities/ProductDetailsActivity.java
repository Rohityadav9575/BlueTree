package com.example.bluetreeassignment.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.bluetreeassignment.Models.Product;
import com.example.bluetreeassignment.Network.ApiClient;
import com.example.bluetreeassignment.Network.ApiService;
import com.example.bluetreeassignment.R;
import com.example.bluetreeassignment.utils.SQLiteHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductDetailsActivity extends AppCompatActivity {
    private TextView title, description, price, category, stock, brand, rating,usernameTextView;
    private ImageView productImage;
    private Button addToCart;
    private int productId;
    private SQLiteHelper databaseHelper;
    private TextView user;
    private ImageButton cart,logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_details);

        initViews();

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });



        logoutButton.setOnClickListener(v -> {
           logout();
        });



        databaseHelper = SQLiteHelper.getInstance(this);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "default_username");


        usernameTextView = findViewById(R.id.username);
        usernameTextView.setText(username);

        productId = getIntent().getIntExtra("PRODUCT_ID", -1);

        if (productId != -1) {
            fetchProductDetails(productId);
        }

        addToCart.setOnClickListener(v -> addToCart());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void logout() {

        SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
        sqLiteHelper.clearCart();


        Intent intent = new Intent(this, LandingActivity.class); // Replace with your landing page activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void initViews() {
        cart = findViewById(R.id.cart);
        logoutButton = findViewById(R.id.logout);
        title = findViewById(R.id.productTitle);
        description = findViewById(R.id.productDescription);
        price = findViewById(R.id.productPrice);
        category = findViewById(R.id.productCategory);
        stock = findViewById(R.id.productStock);
        brand = findViewById(R.id.productBrand);
        rating = findViewById(R.id.productRating);
        productImage = findViewById(R.id.productImage);
        addToCart = findViewById(R.id.addToCart);


    }

    private void fetchProductDetails(int productId) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Make API call to fetch product details using the product ID
        Call<Product> call = apiService.getProductDetails(productId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Product product = response.body();
                    updateUI(product);
                    addToCart.setTag(product);
                } else {
                    Toast.makeText(ProductDetailsActivity.this, "Failed to load product details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(Product product) {

        title.setText(product.getTitle());
        description.setText(product.getDescription());
        price.setText("$" + product.getPrice());
        category.setText(product.getCategory());
        stock.setText("Stock: " + product.getStock());
        brand.setText("Brand: " + product.getBrand());
        rating.setText("Rating: " + product.getRating());


        Glide.with(this)
                .load(product.getImages().get(0))
                .into(productImage);
    }

    private void addToCart() {
        Product product = (Product) addToCart.getTag();
        if (product == null) return;


        boolean isAdded = databaseHelper.addProductToCart(product, 1);

        if (isAdded) {
            Toast.makeText(this, product.getTitle() + " added to cart!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add product to cart", Toast.LENGTH_SHORT).show();
        }
    }
}