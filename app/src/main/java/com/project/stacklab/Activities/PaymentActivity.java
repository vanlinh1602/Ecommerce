package com.project.stacklab.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project.stacklab.Database.AppDatabase;
import com.project.stacklab.Models.PaymentModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.ActivityPaymentBinding;

import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    ActivityPaymentBinding binding;

    AppDatabase db;

    PaymentModel payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getInstance(this);

        List<PaymentModel> list = db.paymentDao().getPayment();

        if (list.size() > 0) {
            payment = list.get(0);
            binding.etNamne.setText(payment.name);
            binding.etPhone.setText(payment.phone);
            binding.etAddress.setText(payment.address);
            binding.etCity.setText(payment.city);
            binding.etZip.setText(payment.zipcode);
            binding.etEmail.setText(payment.email);
        }
        binding.back.setOnClickListener(view -> {
            finish();
        });

        binding.save.setOnClickListener(view -> {
            PaymentModel newPayment = new PaymentModel();
            newPayment.name = binding.etNamne.getText().toString();
            newPayment.phone = binding.etPhone.getText().toString();
            newPayment.address = binding.etAddress.getText().toString();
            newPayment.city = binding.etCity.getText().toString();
            newPayment.zipcode = binding.etZip.getText().toString();
            newPayment.email = binding.etEmail.getText().toString();

            if (payment == null) {
                db.paymentDao().insertPayment(newPayment);
            } else  {
                db.paymentDao().updatePayment(payment.id, newPayment.name, newPayment.email, newPayment.phone, newPayment.address, newPayment.city, newPayment.zipcode);
            }
            Toast.makeText(this, "Payment info saved", Toast.LENGTH_SHORT).show();
            finish();
        });



    }
}