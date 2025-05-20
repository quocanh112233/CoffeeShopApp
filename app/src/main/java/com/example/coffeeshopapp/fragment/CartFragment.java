package com.example.coffeeshopapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.adapter.CartAdapter;
import com.example.coffeeshopapp.controller.CartController;

public class CartFragment extends Fragment {
    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;
    private CartController cartController;
    private BroadcastReceiver cartUpdateReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerViewCart = view.findViewById(R.id.recyclerViewCart);
        cartController = new CartController(requireContext());

        recyclerViewCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        cartAdapter = new CartAdapter(requireContext(), cartController.getCartItems(), cartController);
        recyclerViewCart.setAdapter(cartAdapter);

        cartUpdateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("CART_UPDATED".equals(intent.getAction())) {
                    cartAdapter.updateCartItems(cartController.getCartItems());
                }
            }
        };
        LocalBroadcastManager.getInstance(requireContext())
                .registerReceiver(cartUpdateReceiver, new IntentFilter("CART_UPDATED"));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cartAdapter.updateCartItems(cartController.getCartItems());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(cartUpdateReceiver);
    }
}