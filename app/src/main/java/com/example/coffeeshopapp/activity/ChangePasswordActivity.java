package com.example.coffeeshopapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.controller.ChangePasswordController;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText edtCurrPassword, edtNewPassword, edtConfirmNewPassword;
    Button btnUpdate;
    ImageView back, exit;
    ChangePasswordController changePasswordController;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        edtCurrPassword = findViewById(R.id.edtCurrPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmNewPassword = findViewById(R.id.edtConfirmNewPassword);
        btnUpdate = findViewById(R.id.btnUpdate);
        back = findViewById(R.id.back);
        exit = findViewById(R.id.exit);

        changePasswordController = new ChangePasswordController(this);

        email = getIntent().getStringExtra("USER_EMAIL");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = edtCurrPassword.getText().toString().trim();
                String newPassword = edtNewPassword.getText().toString().trim();
                String confirmNewPassword = edtConfirmNewPassword.getText().toString().trim();

                if (changePasswordController.handleChangePassword(email, currentPassword, newPassword, confirmNewPassword)) {
                    Intent intent = new Intent(ChangePasswordActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePasswordActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}