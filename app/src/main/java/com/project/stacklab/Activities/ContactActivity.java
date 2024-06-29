package com.project.stacklab.Activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project.stacklab.R;
import com.project.stacklab.databinding.ActivityContactBinding;

public class ContactActivity extends AppCompatActivity {

    ActivityContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> {
            finish();
        });
    }
}