package com.project.stacklab.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.project.stacklab.Authentication.SessionManager;
import com.project.stacklab.Database.AppDatabase;
import com.project.stacklab.Models.CategoryModel;
import com.project.stacklab.Models.ItemModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.ActivityVerifyPhoneBinding;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    ActivityVerifyPhoneBinding binding;
    ProgressDialog progressDialog;
    String verificationId, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(VerifyPhoneActivity.this);
        progressDialog.setTitle("Please wait.");
        phone = getIntent().getStringExtra("phone");

        if(phone==null){
            Toast.makeText(this, "Invalid phone.", Toast.LENGTH_SHORT).show();
            finish();
        }

        binding.message.setText("We sent the code to "+phone);

        sendVerificationCode("+91"+phone);

        binding.verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = binding.otpView.getText().toString();
                if (otp.length() == 6) {
                    verifyCode(otp);
                } else {
                    Toast.makeText(VerifyPhoneActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void sendVerificationCode(String phoneNumber) {
        progressDialog.show();
        // Skip OTP verification by directly calling the method to handle successful verification
        signInWithPhoneAuthCredential(null); // Pass null instead of actual PhoneAuthCredential
    }

    private void verifyCode(String codeByUser) {
        progressDialog.show();
        // Verify with any OTP provided by the user
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, codeByUser);
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        // Here you are bypassing the actual OTP verification process
        // You can directly proceed with the user authentication
        progressDialog.dismiss();
        Toast.makeText(VerifyPhoneActivity.this, "Verification Successful", Toast.LENGTH_SHORT).show();

        createDatabase();
        SessionManager sessionManager = new SessionManager(VerifyPhoneActivity.this);
        sessionManager.createLoginSession(FirebaseAuth.getInstance().getUid(), phone);
        Intent intent = new Intent(VerifyPhoneActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void createDatabase() {
        progressDialog.show();


        AppDatabase db = AppDatabase.getInstance(VerifyPhoneActivity.this);

        Random random = new Random();

        String[] categories = {"Nike", "Puma", "Rebook", "Adidas"};
        int[] prices = {500,600,300,200,700,800,900,1100};
        String[] types = {"Sneaker", "Sport", "Casual", "Formal"};
        String[] links = {"https://ananas.vn/wp-content/uploads/Pro_AV00205_2.jpeg",
                "https://ananas.vn/wp-content/uploads/Pro_AV00207_2.jpg",
                "https://ananas.vn/wp-content/uploads/Pro_AV00208_2.jpg",
                "https://ananas.vn/wp-content/uploads/Pro_AV00209_2.jpg",
                "https://ananas.vn/wp-content/uploads/Pro_AV00206_2.jpeg"};


        for (String s : categories) {
            db.categoryDao().insertCategory(new CategoryModel(s));
            for (int i = 0; i < 10; i++) {
                ItemModel randomShoe = new ItemModel();
                randomShoe.name = "Ananas " + i;
                randomShoe.price = prices[random.nextInt(prices.length)];
                randomShoe.image = links[random.nextInt(links.length)];
                randomShoe.type = types[random.nextInt(types.length)];
                randomShoe.category = categories[random.nextInt(categories.length)];
                randomShoe.findId = randomShoe.name + randomShoe.price + randomShoe.type + randomShoe.category;
                randomShoe.description = "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.";
                db.itemDao().insertItem(randomShoe);
            }
        }

    }
}
