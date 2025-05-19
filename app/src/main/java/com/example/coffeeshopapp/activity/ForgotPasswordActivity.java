package com.example.coffeeshopapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.controller.ForgotPasswordController;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText edtEmail, edtNewPassword, edtConfirmNewPassword;
    Button btnResetPassword;
    ImageView back;
    ForgotPasswordController forgotPasswordController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtEmail = findViewById(R.id.edtEmail);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmNewPassword = findViewById(R.id.edtConfirmNewPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        back = findViewById(R.id.back);

        forgotPasswordController = new ForgotPasswordController(this);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String newPassword = edtNewPassword.getText().toString().trim();
                String confirmNewPassword = edtConfirmNewPassword.getText().toString().trim();

                if (forgotPasswordController.handleResetPassword(email, newPassword, confirmNewPassword)) {
                    Intent intent = new Intent(ForgotPasswordActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}