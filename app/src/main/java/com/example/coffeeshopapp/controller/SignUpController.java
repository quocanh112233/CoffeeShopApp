package com.example.coffeeshopapp.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.coffeeshopapp.model.DatabaseHelper;

public class SignUpController {
    private Context context;
    private DatabaseHelper databaseHelper;

    public SignUpController(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    public boolean handleSignUp(String username, String email, String password, String confirmPassword) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (databaseHelper.isEmailTaken(email)) {
            Toast.makeText(context, "Email is already registered", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean success = databaseHelper.addUser(username, email, password);
        if (success) {
            Toast.makeText(context, "Sign Up successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Sign Up failed", Toast.LENGTH_SHORT).show();
        }
        return success;
    }
}
