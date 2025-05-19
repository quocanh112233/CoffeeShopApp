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
import com.example.coffeeshopapp.model.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class CheckOutActivity extends AppCompatActivity {
    ImageView imgProduct, back;
    private TextView tvDate, tvInvoiceId, tvProductName, tvCost, tvQuantity, tvTotalPrice;
    private Button btnCheckOut;
    private CartController cartController;
    private DatabaseHelper databaseHelper;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        imgProduct = findViewById(R.id.imgProduct);
        back = findViewById(R.id.back);
        tvDate = findViewById(R.id.tvDate);
        tvInvoiceId = findViewById(R.id.tvInvoiceId);
        tvProductName = findViewById(R.id.tvProductName);
        tvCost = findViewById(R.id.tvCost);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnCheckOut = findViewById(R.id.btnCheckout);

        cartController = new CartController(this);
        databaseHelper = new DatabaseHelper(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        tvDate.setText("Payment Date: " + currentDate);

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
        String orderId = generateOrderId();
        tvInvoiceId.setText("Order ID: " + orderId);
        Glide.with(this)
                .load(productImagePath)
                .placeholder(R.drawable.img_placeholder)
                .into(imgProduct);

        btnCheckOut.setOnClickListener(v -> {
            boolean success = databaseHelper.addOrder(orderId, productId, productName, quantity, totalPrice, currentDate);
            if (success) {
                Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Lưu giao dịch thất bại", Toast.LENGTH_SHORT).show();
            }
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