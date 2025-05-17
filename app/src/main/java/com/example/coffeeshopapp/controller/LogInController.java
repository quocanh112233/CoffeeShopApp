package com.example.coffeeshopapp.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.coffeeshopapp.model.DatabaseHelper;

public class LogInController {
    private Context context;
    private DatabaseHelper databaseHelper;

    public LogInController(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    public int handleLogIn(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return 0;
        }

        boolean isValid = databaseHelper.checkUser(email, password);
        if (!isValid) {
            Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show();
            return 0;
        }

        if (email.equals("admin123@admin.com") && password.equals("admin!123")) {
            return 2;
        }
        return 1;
    }
}
