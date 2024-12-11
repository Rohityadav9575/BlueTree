package com.example.bluetreeassignment.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.bluetreeassignment.Network.ApiClient;
import com.example.bluetreeassignment.Network.ApiService;
import com.example.bluetreeassignment.R;
import com.google.android.gms.analytics.ecommerce.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {
    /*private TextView productTitle, productDescription, productPrice, productRating, productStock;
    private ImageView productImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_details);
        // Initialize views
        productTitle = findViewById(R.id.productTitle);
        productDescription = findViewById(R.id.productDescription);
        productPrice = findViewById(R.id.productPrice);
        productRating = findViewById(R.id.productRating);
        productStock = findViewById(R.id.productStock);
        productImage = findViewById(R.id.productImage);

        // Get the product ID passed from the adapter
        int productId = getIntent().getIntExtra("PRODUCT_ID", -1);

        // Fetch product details using the product ID from the API or database
        fetchProductDetails(productId);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void fetchProductDetails(int productId) {
        // You can use your API client to fetch details using the productId.
        // For example, assuming you're using Retrofit or another networking library:

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Product> call = apiService.getProductDetails(productId);

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Product product = response.body();
                    // Populate views with product details
                    productTitle.setText(product.getTitle());
                    productDescription.setText(product.getDescription());
                    productPrice.setText("$" + product.getPrice());
                    productRating.setText("Rating: " + product.getRating());
                    productStock.setText("Stock: " + product.getStock());

                    Glide.with(ProductDetailsActivity.this)
                            .load(product.getImages().get(0))  // Assuming first image as main
                            .placeholder(R.drawable.placeholder)
                            .into(productImage);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                // Handle failure
                Log.e("ProductDetails", "Failed to fetch product details", t);
            }
        });
    }*/
}
