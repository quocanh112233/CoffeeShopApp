package com.example.coffeeshopapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.adapter.CartAdapter;
import com.example.coffeeshopapp.controller.CartController;

public class CartFragment extends Fragment {
    RecyclerView recyclerViewCart;
    CartAdapter cartAdapter;
    CartController cartController;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerViewCart = view.findViewById(R.id.recyclerViewCart);
        cartController = new CartController(requireContext());

        recyclerViewCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        cartAdapter = new CartAdapter(requireContext(), cartController.getCartItems(), cartController);
        recyclerViewCart.setAdapter(cartAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cartAdapter.updateCartItems(cartController.getCartItems());
    }
}