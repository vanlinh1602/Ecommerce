package com.project.stacklab.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.project.stacklab.Adapters.OrderAdapter;
import com.project.stacklab.Database.AppDatabase;
import com.project.stacklab.Models.OrderModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.ActivityOrderBinding;

import java.util.List;

public class OrderActivity extends AppCompatActivity {

    AppDatabase db;

    List<OrderModel> models;
    ActivityOrderBinding binding;

    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getInstance(OrderActivity.this);

        models = db.orderDao().getOrders();

        binding.back.setOnClickListener(view -> {
            finish();
        });

        orderAdapter = new OrderAdapter(OrderActivity.this, models, new OrderAdapter.OrderSelectListener() {
            @Override
            public void onOrderSelected(OrderModel orderModel) {
                Intent intent = new Intent(OrderActivity.this, OrderDetailActivity.class);
                intent.putExtra("orderId", orderModel.orderId);
                startActivity(intent);
            }
        });

        binding.rvItems.setAdapter(orderAdapter);
        binding.rvItems.setLayoutManager(new GridLayoutManager(OrderActivity.this, 1));
    }
}