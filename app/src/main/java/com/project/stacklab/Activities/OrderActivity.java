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
import com.project.stacklab.Authentication.SessionManager;
import com.project.stacklab.Database.AppDatabase;
import com.project.stacklab.Models.OrderModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.ActivityOrderBinding;

import java.util.List;

public class OrderActivity extends AppCompatActivity {

    AppDatabase db;

    SessionManager s;

    List<OrderModel> models;
    ActivityOrderBinding binding;

    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getInstance(OrderActivity.this);

        s = new SessionManager(this);

        models = db.orderDao().getOrders();

        if (s.isAdmin()) {
            binding.tvOrder.setText("Orders");
        }

        binding.back.setOnClickListener(view -> {
            finish();
        });

        orderAdapter = new OrderAdapter(OrderActivity.this, models, new OrderAdapter.OrderSelectListener() {
            @Override
            public void onOrderSelected(OrderModel orderModel) {
                Intent intent = new Intent(OrderActivity.this, OrderDetailActivity.class);
                intent.putExtra("orderId", orderModel.orderId);
                startActivityForResult(intent, 2);
            }
        });

        binding.rvItems.setAdapter(orderAdapter);
        binding.rvItems.setLayoutManager(new GridLayoutManager(OrderActivity.this, 1));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the request code is the same as what we passed to startActivityForResult()
        if (requestCode == 2) {
            // If the result code is RESULT_OK, that means the ProductEditorActivity finished successfully
            if (resultCode == RESULT_OK) {
                // Refresh the items list
                models = db.orderDao().getOrders();
                orderAdapter.setItemList(models);
                orderAdapter.notifyDataSetChanged();
            }
        }
    }
}