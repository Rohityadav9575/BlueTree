package com.example.bluetreeassignment.Network;

import com.example.bluetreeassignment.Models.Product;
import com.example.bluetreeassignment.Models.ProductList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("products")
    Call<ProductList> getProducts();
}
