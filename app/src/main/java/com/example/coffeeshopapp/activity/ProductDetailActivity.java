package com.example.coffeeshopapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.controller.CartController;
import com.example.coffeeshopapp.model.CartItem;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView imgProduct,back;
    TextView tvProductName, tvCost, tvDescription;
    Button btnAddToCart;
    CartController cartController;
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imgProduct = findViewById(R.id.imgProduct);
        tvProductName = findViewById(R.id.tvProductName);
        tvCost = findViewById(R.id.tvCost);
        tvDescription = findViewById(R.id.tvDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        back = findViewById(R.id.back);

        cartController = new CartController(this);

        int productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        String productName = getIntent().getStringExtra("PRODUCT_NAME");
        double productCost = getIntent().getDoubleExtra("PRODUCT_COST", 0.0);
        String productDescription = getIntent().getStringExtra("PRODUCT_DESCRIPTION");
        String productImagePath = getIntent().getStringExtra("PRODUCT_IMAGE_PATH");

        tvProductName.setText(productName);
        tvCost.setText(String.format("$%.2f", productCost));
        tvDescription.setText(productDescription);
        Glide.with(this)
                .load(productImagePath)
                .placeholder(R.drawable.img_placeholder)
                .into(imgProduct);

        btnAddToCart.setOnClickListener(v -> {
            CartItem item = new CartItem(productId, productName, productCost, productImagePath, 1);
            cartController.addToCart(item);
        });
        back.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}