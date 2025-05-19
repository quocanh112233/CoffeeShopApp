package com.example.coffeeshopapp.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.activity.ChangePasswordActivity;
import com.example.coffeeshopapp.activity.LogInActivity;
import com.example.coffeeshopapp.activity.PaymentHistoryActivity;
import com.example.coffeeshopapp.model.DatabaseHelper;

public class AccountFragment extends Fragment {
    Button btnChangePass, btnPaymentHistory;
    String email;
    ImageView exit;
    DatabaseHelper databaseHelper;
    TextView tvUsername, tvEmail;
    View view;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_account, container, false);

        tvUsername = view.findViewById(R.id.tvUsername);
        tvEmail = view.findViewById(R.id.tvEmail);
        databaseHelper = new DatabaseHelper(requireContext());
        btnChangePass = view.findViewById(R.id.btnChangePass);
        btnPaymentHistory = view.findViewById(R.id.btnPaymentHistory);
        exit = view.findViewById(R.id.exit);
        if (getArguments() != null) {
            email = getArguments().getString("USER_EMAIL");
        }
        Bundle args = getArguments();
        String email = args != null ? args.getString("USER_EMAIL", "") : "";
        if (!email.isEmpty()) {
            String username = databaseHelper.getUsernameByEmail(email);
            tvUsername.setText("Username: " + (username.isEmpty() ? "Unknown" : username));
            tvEmail.setText("Email: " + email);
        } else {
            tvUsername.setText("Username: Not logged in");
            tvEmail.setText("Email: Not logged in");
        }
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                intent.putExtra("USER_EMAIL", email);
                startActivity(intent);
            }
        });
        btnPaymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PaymentHistoryActivity.class);
                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}