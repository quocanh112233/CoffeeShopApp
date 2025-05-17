package com.example.coffeeshopapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.controller.CartController;

import java.util.Random;

public class CheckOutActivity extends AppCompatActivity {
    ImageView imgProduct, back;
    private TextView tvInvoiceId, tvProductName, tvCost, tvQuantity, tvTotalPrice;
    private Button btnCheckOut;
    private CartController cartController;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        imgProduct = findViewById(R.id.imgProduct);
        back = findViewById(R.id.back);
        tvInvoiceId = findViewById(R.id.tvInvoiceId);
        tvProductName = findViewById(R.id.tvProductName);
        tvCost = findViewById(R.id.tvCost);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnCheckOut = findViewById(R.id.btnCheckout);

        cartController = new CartController(this);

        int productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        String productName = getIntent().getStringExtra("PRODUCT_NAME");
        double productCost = getIntent().getDoubleExtra("PRODUCT_COST", 0.0);
        int quantity = getIntent().getIntExtra("PRODUCT_QUANTITY", 1);
        String productImagePath = getIntent().getStringExtra("PRODUCT_IMAGE_PATH");

        tvProductName.setText(productName);
        tvCost.setText(String.format("$%.2f", productCost));
        tvQuantity.setText("Quantity: " + quantity);
        double totalPrice = productCost * quantity;
        tvTotalPrice.setText(String.format("Total: $%.2f", totalPrice));
        tvInvoiceId.setText("Order ID: " + generateOrderId());
        Glide.with(this)
                .load(productImagePath)
                .placeholder(R.drawable.img_placeholder)
                .into(imgProduct);

        btnCheckOut.setOnClickListener(v -> {
            Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
        });
        back.setOnClickListener(v -> finish());
    }
    private String generateOrderId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder orderId = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            orderId.append(characters.charAt(random.nextInt(characters.length())));
        }
        return orderId.toString();
    }
}