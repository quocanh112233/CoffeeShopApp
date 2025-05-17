package com.example.coffeeshopapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.adapter.ProductAdapter;
import com.example.coffeeshopapp.controller.AdminController;
import com.example.coffeeshopapp.model.DatabaseHelper;
import com.example.coffeeshopapp.model.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AdminActivity extends AppCompatActivity {
    ImageView imgProduct,imgInsert, imgDelete, imgExit;
    EditText edtProductName, edtCost, edtDescription;
    Button btnAddImage;
    RecyclerView recyclerViewProducts;
    DatabaseHelper databaseHelper;
    ProductAdapter productAdapter;
    String selectedImagePath;
    ActivityResultLauncher<Intent> imagePickerLauncher;
    Product selectedProduct;
    AdminController adminController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        imgProduct = findViewById(R.id.imgProduct);
        imgInsert = findViewById(R.id.insert);
        imgDelete = findViewById(R.id.delete);
        imgExit = findViewById(R.id.exit);
        edtProductName = findViewById(R.id.edtProductName);
        edtCost = findViewById(R.id.edtCost);
        edtDescription = findViewById(R.id.edtDescription);
        btnAddImage = findViewById(R.id.btnAddImage);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);

        adminController = new AdminController(this);
        databaseHelper = new DatabaseHelper(this);

        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(this, databaseHelper.getAllProducts(), product -> {
            selectedProduct = product;
        });
        recyclerViewProducts.setAdapter(productAdapter);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                try {
                    selectedImagePath = saveImageToInternalStorage(imageUri);
                    Glide.with(this).load(selectedImagePath).into(imgProduct);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnAddImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        imgInsert.setOnClickListener(v -> {
            String name = edtProductName.getText().toString().trim();
            String cost = edtCost.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();
            if (adminController.addProduct(name, cost, description, selectedImagePath)) {
                productAdapter.updateProducts(databaseHelper.getAllProducts());
                clearInputs();
            }
        });

        imgDelete.setOnClickListener(v -> {
            if (selectedProduct != null) {
                if (adminController.deleteProduct(selectedProduct.getId())) {
                    productAdapter.updateProducts(databaseHelper.getAllProducts());
                    selectedProduct = null;
                }
            } else {
                adminController.deleteProduct(-1);
            }
        });

        imgExit.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private String saveImageToInternalStorage(Uri imageUri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(imageUri);
        File directory = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "CoffeeShopApp");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(directory, "product_" + System.currentTimeMillis() + ".jpg");
        FileOutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.close();
        return file.getAbsolutePath();
    }

    private void clearInputs() {
        edtProductName.setText("");
        edtCost.setText("");
        edtDescription.setText("");
        imgProduct.setImageResource(R.drawable.img_placeholder);
        selectedImagePath = null;
    }
}