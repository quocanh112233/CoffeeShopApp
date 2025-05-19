package com.example.coffeeshopapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.fragment.AccountFragment;
import com.example.coffeeshopapp.fragment.CartFragment;
import com.example.coffeeshopapp.fragment.HomeFragment;
import com.example.coffeeshopapp.fragment.ProductListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(navListener);

        String email = getIntent().getStringExtra("USER_EMAIL");
        Fragment selectFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("USER_EMAIL", email != null ? email : "");
        selectFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();
    }

    private final NavigationBarView.OnItemSelectedListener navListener = item -> {
        int itemId = item.getItemId();
        Fragment selectedFragment = null;
        Bundle bundle = new Bundle();
        String email = getIntent().getStringExtra("USER_EMAIL");
        bundle.putString("USER_EMAIL", email != null ? email : "");

        if (itemId == R.id.nav_home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.nav_product_list) {
            selectedFragment = new ProductListFragment();
        } else if (itemId == R.id.nav_cart) {
            selectedFragment = new CartFragment();
        } else if (itemId == R.id.nav_account) {
            selectedFragment = new AccountFragment();
        }

        if (selectedFragment != null) {
            selectedFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
        }
        return true;
    };
}