package com.example.coffeeshopapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.adapter.ProductAdapter;
import com.example.coffeeshopapp.model.DatabaseHelper;
import com.example.coffeeshopapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewProducts = view.findViewById(R.id.recyclerViewProducts);
        databaseHelper = new DatabaseHelper(requireContext());

        List<Product> productList = getLimitedProducts(3);
        productAdapter = new ProductAdapter(requireContext(), productList);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewProducts.setAdapter(productAdapter);

        return view;
    }

    private List<Product> getLimitedProducts(int limit) {
        List<Product> allProducts = databaseHelper.getAllProducts();
        List<Product> limitedProducts = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, allProducts.size()); i++) {
            limitedProducts.add(allProducts.get(i));
        }
        return limitedProducts;
    }
}