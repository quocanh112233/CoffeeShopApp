package com.example.coffeeshopapp.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.coffeeshopapp.model.DatabaseHelper;

public class ForgotPasswordController {
    private Context context;
    private DatabaseHelper databaseHelper;

    public ForgotPasswordController(Context context) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    public boolean handleResetPassword(String email, String newPassword, String confirmNewPassword) {
        if (email.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!databaseHelper.isEmailTaken(email)) {
            Toast.makeText(context, "Email hasn't existed yet", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(context, "Mật khẩu và xác nhận mật khẩu mới phải giống nhau", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean success = databaseHelper.updatePassword(email, newPassword);
        if (success) {
            Toast.makeText(context, "Password reset successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Password reset failed", Toast.LENGTH_SHORT).show();
        }
        return success;
    }
}
