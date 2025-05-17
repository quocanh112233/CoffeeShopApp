package com.example.coffeeshopapp.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.coffeeshopapp.model.DatabaseHelper;

public class ChangePasswordController {
    private Context context;
    private DatabaseHelper databaseHelper;

    public ChangePasswordController(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    public boolean handleChangePassword(String email, String currentPassword, String newPassword, String confirmNewPassword) {
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            Toast.makeText(context, "Please Fill Out All The Information", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!databaseHelper.checkUser(email, currentPassword)) {
            Toast.makeText(context, "Error Current Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(context, "Error New Password Or Confirm New Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean success = databaseHelper.updatePassword(email, newPassword);
        if (success) {
            Toast.makeText(context, "Password updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to update password", Toast.LENGTH_SHORT).show();
        }
        return success;
    }
}
