package com.example.coffeeshopapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.adapter.PaymentHistoryAdapter;
import com.example.coffeeshopapp.model.DatabaseHelper;
import com.example.coffeeshopapp.model.Order;

import java.util.List;

public class PaymentHistoryActivity extends AppCompatActivity {
    ImageView back;
    RecyclerView recyclerViewPaymentHistory;
    PaymentHistoryAdapter paymentHistoryAdapter;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        back = findViewById(R.id.back);
        recyclerViewPaymentHistory = findViewById(R.id.recyclerViewPaymentHistory);
        databaseHelper = new DatabaseHelper(this);
        List<Order> orderList = databaseHelper.getAllOrders();

        paymentHistoryAdapter = new PaymentHistoryAdapter(this, orderList);
        recyclerViewPaymentHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPaymentHistory.setAdapter(paymentHistoryAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}