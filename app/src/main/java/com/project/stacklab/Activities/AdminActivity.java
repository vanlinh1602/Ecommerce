package com.project.stacklab.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.project.stacklab.Authentication.SessionManager;
import com.project.stacklab.Helpers.CartManager;
import com.project.stacklab.R;
import com.project.stacklab.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {

    ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SessionManager s = new SessionManager(this);

        binding.logout.setOnClickListener(view -> {
            s.logoutSession();
            FirebaseAuth.getInstance().signOut();
            CartManager.clearCart(this);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.finish();
        });

        binding.orderManagement.setOnClickListener(view -> {
            startActivity(new Intent(this, OrderActivity.class));
        });

        binding.revenueManagement.setOnClickListener(view -> {
            startActivity(new Intent(this, RevenueActivity.class));
        });
    }
}