package com.project.stacklab.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.project.stacklab.Adapters.OrderAdapter;
import com.project.stacklab.Adapters.OrderDetailAdapter;
import com.project.stacklab.Authentication.SessionManager;
import com.project.stacklab.Database.AppDatabase;
import com.project.stacklab.Models.OrderDetailModel;
import com.project.stacklab.Models.OrderModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.ActivityOrderBinding;
import com.project.stacklab.databinding.ActivityOrderDetailBinding;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    AppDatabase db;

    List<OrderDetailModel> details;
    OrderModel order;
    ActivityOrderDetailBinding binding;
    OrderDetailAdapter adapter;

    boolean isAdmin = false;

    boolean changeStatus = false;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(OrderDetailActivity.this);
        isAdmin = sessionManager.isAdmin();

        db = AppDatabase.getInstance(OrderDetailActivity.this);

        String orderId = getIntent().getStringExtra("orderId");

        order = db.orderDao().getOrder(orderId);

        details = db.orderDetailDao().getOrderDetaills(orderId);

        binding.date.setText(order.date);
        binding.discount.setText(order.discount.toString());
        binding.total.setText((order.totalPrice - order.discount) + "");
        binding.status.setText(order.status);

        binding.back.setOnClickListener(view -> {
            if (isAdmin && changeStatus){
                setResult(RESULT_OK); // Set the result before finishing the activity
                finish();
            } else {
                finish();
            }
        });

        adapter = new OrderDetailAdapter(OrderDetailActivity.this, details);
        binding.rvItems.setAdapter(adapter);
        binding.rvItems.setLayoutManager(new GridLayoutManager(OrderDetailActivity.this, 1));

        if (!isAdmin) {
            binding.btnAdmin.setVisibility(View.GONE);
        } else {
            binding.pending.setOnClickListener(view -> {
                db.orderDao().updateOrderStatus( order.id,"Pending");
                Toast.makeText(OrderDetailActivity.this, "Order status updated to Pending", Toast.LENGTH_SHORT).show();
                changeStatus = true;
                binding.status.setText("Pending");
            });

            binding.success.setOnClickListener(view -> {
                db.orderDao().updateOrderStatus( order.id,"Success");
                Toast.makeText(OrderDetailActivity.this, "Order status updated to Success", Toast.LENGTH_SHORT).show();
                changeStatus = true;
                binding.status.setText("Success");
            });

            binding.cancelled.setOnClickListener(view -> {
                db.orderDao().updateOrderStatus( order.id,"Cancelled");
                Toast.makeText(OrderDetailActivity.this, "Order status updated to Cancelled", Toast.LENGTH_SHORT).show();
                changeStatus = true;
                binding.status.setText("Cancelled");
            });
        }
    }
}