package com.example.coffeeshopapp.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.coffeeshopapp.model.DatabaseHelper;

public class AdminController {
    private Context context;
    private DatabaseHelper databaseHelper;

    public AdminController(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    public boolean addProduct(String name, String cost, String description, String imagePath) {
        // Kiểm tra các trường không trống
        if (name.isEmpty() || cost.isEmpty() || description.isEmpty()) {
            Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra đã chọn ảnh
        if (imagePath == null) {
            Toast.makeText(context, "Please select an image", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra tên sản phẩm trùng
        if (databaseHelper.isProductNameTaken(name)) {
            Toast.makeText(context, "Product name already exists", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra giá hợp lệ
        double costValue;
        try {
            costValue = Double.parseDouble(cost);
            if (costValue <= 0) {
                Toast.makeText(context, "Cost must be greater than 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Invalid cost format", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean success = databaseHelper.addProduct(name, costValue, description, imagePath);
        if (success) {
            Toast.makeText(context, "Product added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to add product", Toast.LENGTH_SHORT).show();
        }
        return success;
    }

    public boolean deleteProduct(int productId) {
        if (productId == -1) {
            Toast.makeText(context, "Please select a product to delete", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean success = databaseHelper.deleteProduct(productId);
        if (success) {
            Toast.makeText(context, "Product deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to delete product", Toast.LENGTH_SHORT).show();
        }
        return success;
    }
}
