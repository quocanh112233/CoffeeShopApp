package com.example.coffeeshopapp.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.coffeeshopapp.model.CartItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartController {
    private Context context;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private static final String PREF_NAME = "CartPrefs";
    private static final String CART_KEY = "cart_items";

    public CartController(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    public boolean addToCart(CartItem item) {
        if (item.getQuantity() < 0 || item.getQuantity() > 99) {
            Toast.makeText(context, "Quantity must be between 0 and 99", Toast.LENGTH_SHORT).show();
            return false;
        }
        List<CartItem> cartItems = getCartItems();
        cartItems.add(item);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CART_KEY, gson.toJson(cartItems));
        editor.apply();
        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
        return true;
    }

    public List<CartItem> getCartItems() {
        String json = sharedPreferences.getString(CART_KEY, "");
        if (json.isEmpty()) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<CartItem>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public boolean removeFromCart(int productId) {
        List<CartItem> cartItems = getCartItems();
        boolean removed = cartItems.removeIf(item -> item.getProductId() == productId);
        if (removed) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(CART_KEY, gson.toJson(cartItems));
            editor.apply();
            Toast.makeText(context, "Removed from cart", Toast.LENGTH_SHORT).show();
        }
        return removed;
    }

    public boolean updateQuantity(int productId, int newQuantity) {
        if (newQuantity < 0 || newQuantity > 99) {
            Toast.makeText(context, "Quantity must be between 0 and 99", Toast.LENGTH_SHORT).show();
            return false;
        }
        List<CartItem> cartItems = getCartItems();
        for (CartItem item : cartItems) {
            if (item.getProductId() == productId) {
                item.setQuantity(newQuantity);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(CART_KEY, gson.toJson(cartItems));
                editor.apply();
                return true;
            }
        }
        return false;
    }

    public double getTotalPrice() {
        List<CartItem> cartItems = getCartItems();
        double total = 0.0;
        for (CartItem item : cartItems) {
            total += item.getCost() * item.getQuantity();
        }
        return total;
    }
}
