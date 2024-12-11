package com.example.bluetreeassignment.Models;

import  com.example.bluetreeassignment.Models.Product;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductList {
       @SerializedName("products")
        private List<Product> products;

        public List<Product> getProducts() {
            return products;
        }


    }

