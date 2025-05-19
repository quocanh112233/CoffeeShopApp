package com.example.coffeeshopapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.activity.ProductDetailActivity;
import com.example.coffeeshopapp.activity.SearchActivity;
import com.example.coffeeshopapp.adapter.ProductAdapter;
import com.example.coffeeshopapp.model.DatabaseHelper;

public class ProductListFragment extends Fragment {
    View view;
    RecyclerView recyclerViewProduct;
    ProductAdapter productAdapter;
    DatabaseHelper databaseHelper;
    ImageView search;
    SearchView searchView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_product_list, container, false);

        searchView = view.findViewById(R.id.searchView);
        search = view.findViewById(R.id.search);
        recyclerViewProduct = view.findViewById(R.id.recyclerViewProducts);
        databaseHelper = new DatabaseHelper(requireContext());

        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(requireContext()));
        productAdapter = new ProductAdapter(requireContext(), databaseHelper.getAllProducts(), product -> {
            Intent intent = new Intent(requireActivity(), ProductDetailActivity.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            intent.putExtra("PRODUCT_NAME", product.getName());
            intent.putExtra("PRODUCT_COST", product.getCost());
            intent.putExtra("PRODUCT_DESCRIPTION", product.getDescription());
            intent.putExtra("PRODUCT_IMAGE_PATH", product.getImagePath());
            startActivity(intent);
        });
        recyclerViewProduct.setAdapter(productAdapter);

        search.setOnClickListener(v -> {
            String query = searchView.getQuery().toString().trim();
            if (!query.isEmpty()) {
                Intent intent = new Intent(requireContext(), SearchActivity.class);
                intent.putExtra("SEARCH_QUERY", query);
                startActivity(intent);
            }
        });

        return view;
    }
}