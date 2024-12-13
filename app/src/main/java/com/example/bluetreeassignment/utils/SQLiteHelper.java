package com.example.bluetreeassignment.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bluetreeassignment.Models.Product;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cart.db";
    private static final int DATABASE_VERSION = 9; // Increment version for schema change

    private static final String TABLE_CART = "cart";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_THUMBNAIL = "thumbnail";



    private static SQLiteHelper instance;

    public static synchronized SQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SQLiteHelper(context.getApplicationContext());
        }
        return instance;
    }

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createCartTable = "CREATE TABLE " + TABLE_CART + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_THUMBNAIL + " TEXT)";
        db.execSQL(createCartTable);

        // Create User Table

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle schema update, if database version changes
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);

        onCreate(db);
    }

    /**
     * Add or update a product in the cart.
     */
    public boolean addProductToCart(Product product, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the product already exists
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE " + COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())});

        if (cursor != null && cursor.moveToFirst()) {
            // Update the quantity if the product exists
            int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
            quantity += currentQuantity;
            ContentValues values = new ContentValues();
            values.put(COLUMN_QUANTITY, quantity);

            int rowsUpdated = db.update(TABLE_CART, values, COLUMN_ID + " = ?",
                    new String[]{String.valueOf(product.getId())});
            cursor.close();
            db.close();
            return rowsUpdated > 0;
        } else {
            // Insert the new product
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, product.getId());
            values.put(COLUMN_TITLE, product.getTitle());
            values.put(COLUMN_PRICE, product.getPrice());
            values.put(COLUMN_QUANTITY, quantity);
            values.put(COLUMN_THUMBNAIL, product.getThumbnail());

            long result = db.insert(TABLE_CART, null, values);
            db.close();
            if (cursor != null) cursor.close();
            return result != -1;
        }
    }

    /**
     * Fetch all products in the cart as a List.
     */
    public List<Product> getCartItems() {
        List<Product> cartProducts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                product.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
                product.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
                product.setThumbnail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THUMBNAIL)));
                cartProducts.add(product);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return cartProducts;
    }

    /**
     * Remove a specific product from the cart.
     */
    public boolean removeProductFromCart(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_CART, COLUMN_ID + " = ?",
                new String[]{String.valueOf(productId)});
        db.close();
        return rowsDeleted > 0;
    }

    /**
     * Clear all items from the cart.
     */
    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, null, null);
        db.close();
    }

    /**
     * Calculate the total price of all items in the cart.
     */
    public double calculateTotalAmount() {
        double totalAmount = 0.0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PRICE + ", " + COLUMN_QUANTITY + " FROM " + TABLE_CART, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
                totalAmount += price * quantity;
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return totalAmount;
    }

}