package com.example.bluetreeassignment.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ProductList {

    @SerializedName("products")
    private List<Product> products;

    // Default constructor
    public ProductList() {
    }

    // Getter for the list of products
    public List<Product> getProducts() {
        return products;
    }

    // Setter for the list of products
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}