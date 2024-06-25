package com.project.stacklab.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.project.stacklab.Authentication.SessionManager;
import com.project.stacklab.R;
import com.project.stacklab.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManager = new SessionManager(LoginActivity.this);
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = binding.userName.getText().toString();
                String password = binding.password.getText().toString();

                startActivity(new Intent(LoginActivity.this, VerifyPhoneActivity.class)
                        .putExtra("userName", userName).putExtra("password", password));
            }
        });

    }


    public boolean verifyPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10) {
            Toast.makeText(LoginActivity.this, "Phone number should be of 10 digits", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phoneNumber.startsWith("+91")) {
            Toast.makeText(LoginActivity.this, "Phone number should not contain country code", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phoneNumber.contains(" ")) {
            Toast.makeText(LoginActivity.this, "Phone number should not contain spaces", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        if (sessionManager.checkLogin()) {
            if (sessionManager.isAdmin()) {
                startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                finish();
            } else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        } else {
            FirebaseAuth.getInstance().signOut();
        }


    }
}