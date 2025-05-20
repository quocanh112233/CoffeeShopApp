package com.example.coffeeshopapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.adapter.ProductListAdapter;
import com.example.coffeeshopapp.model.DatabaseHelper;
import com.example.coffeeshopapp.model.Product;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ImageView back;
    private RecyclerView recyclerViewSearchResults;
    private ProductListAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        back = findViewById(R.id.back);
        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults);
        databaseHelper = new DatabaseHelper(this);

        String query = getIntent().getStringExtra("SEARCH_QUERY");
        List<Product> productList = query != null ? databaseHelper.searchProducts(query) : databaseHelper.getAllProducts();

        adapter = new ProductListAdapter(this, productList);
        recyclerViewSearchResults.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSearchResults.setAdapter(adapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}