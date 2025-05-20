package com.example.coffeeshopapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.activity.SearchActivity;
import com.example.coffeeshopapp.adapter.ProductListAdapter;
import com.example.coffeeshopapp.model.DatabaseHelper;
import com.example.coffeeshopapp.model.Product;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ProductListFragment extends Fragment {
    private TextInputEditText searchView;
    private ImageView searchButton;
    private RecyclerView recyclerViewProducts;
    private ProductListAdapter adapter;
    private DatabaseHelper databaseHelper;

    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        searchView = view.findViewById(R.id.searchView);
        searchButton = view.findViewById(R.id.search);
        recyclerViewProducts = view.findViewById(R.id.recyclerViewProducts);
        databaseHelper = new DatabaseHelper(requireContext());

        // Thiết lập RecyclerView
        List<Product> productList = databaseHelper.getAllProducts();
        adapter = new ProductListAdapter(requireContext(), productList);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewProducts.setAdapter(adapter);

        // Xử lý sự kiện nhấn nút tìm kiếm
        searchButton.setOnClickListener(v -> {
            String query = searchView.getText() != null ? searchView.getText().toString().trim() : "";
            if (!query.isEmpty()) {
                Intent intent = new Intent(requireContext(), SearchActivity.class);
                intent.putExtra("SEARCH_QUERY", query);
                startActivity(intent);
            }
        });

        return view;
    }
}