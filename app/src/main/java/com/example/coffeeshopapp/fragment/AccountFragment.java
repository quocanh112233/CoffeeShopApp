package com.example.coffeeshopapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.activity.ChangePasswordActivity;
import com.example.coffeeshopapp.activity.LogInActivity;

public class AccountFragment extends Fragment {
    Button btnChangePass;
    String email;
    ImageView exit;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_account, container, false);

        btnChangePass = view.findViewById(R.id.btnChangePass);
        exit = view.findViewById(R.id.exit);
        if (getArguments() != null) {
            email = getArguments().getString("USER_EMAIL");
        }

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                intent.putExtra("USER_EMAIL", email);
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