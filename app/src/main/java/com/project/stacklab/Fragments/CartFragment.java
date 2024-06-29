package com.project.stacklab.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.project.stacklab.Activities.MainActivity;
import com.project.stacklab.Activities.PaymentActivity;
import com.project.stacklab.Adapters.CartAdapter;
import com.project.stacklab.Database.AppDatabase;
import com.project.stacklab.Helpers.UtilsHelper;
import com.project.stacklab.Models.CartModel;
import com.project.stacklab.Models.OrderDetailModel;
import com.project.stacklab.Models.OrderModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.FragmentCartBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CartFragment extends Fragment {
    FragmentCartBinding binding;
    ObservableArrayList<CartModel> cartItems;

    ProgressDialog progressDialog;
    CartAdapter cartAdapter;

    boolean isHaveAddress = false;

    AppDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(getLayoutInflater());
        db = AppDatabase.getInstance(getContext());
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override 
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            cartItems = (ObservableArrayList<CartModel>)
                    getArguments().getSerializable("cartItems");
        }

        isHaveAddress = db.paymentDao().getPayment().size() > 0;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait.");
        progressDialog.setCancelable(false);
        if (cartItems.size() > 0) {
            binding.totalBox.setVisibility(View.VISIBLE);
        } else {
            binding.totalBox.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Cart is Empty.", Toast.LENGTH_SHORT).show();
        }
        initializeCArt();

        binding.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isHaveAddress) {
                    showMessageMissPayment();
                    return;
                }
                showMessageDialog();
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Back Pressed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initializeCArt() {

        cartAdapter = new CartAdapter(getContext(), cartItems, new CartAdapter.CountChangeListener() {
            @Override
            public void onIncreased(CartModel item, int position) {

                setTotal(getTotal());
                binding.headCount.setText(String.valueOf(cartItems.size()));
            }

            @Override
            public void onDecreased(CartModel item, int position) {
                if (item.getCount() < 1) {
                    cartItems.remove(position);
                    cartAdapter.notifyDataSetChanged();
                }binding.headCount.setText(String.valueOf(cartItems.size()));

                setTotal(getTotal());
            }
        });
        binding.headCount.setText(String.valueOf(cartItems.size()));
        binding.rvItems.setAdapter(cartAdapter);
        binding.rvItems.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvItems.setHasFixedSize(true);

        if (cartItems.size() > 0) setTotal(getTotal());

        cartAdapter.notifyDataSetChanged();


    }

    public void setTotal(Integer amount) {
        binding.subTotal.setText(amount.toString());
        binding.total.setText(String.valueOf(amount - 10)); // discount of $10
    }

    private Integer getTotal() {
        Integer total = 0;
        for (CartModel c : cartItems) total += (c.getCount() * c.getItem().getPrice());


        return total;
    }

    private void showMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Your order has been placed successfully.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        OrderModel order = new OrderModel(UtilsHelper.randomStringId(), cartItems.size(), getTotal(), 10, 0, "Pending", UtilsHelper.getCurrentDate());
                        db.orderDao().insertOrder(order);

                        List<OrderDetailModel> orderDetails = new ArrayList<>();
                        for (CartModel c : cartItems) {
                            OrderDetailModel orderDetail = new OrderDetailModel();
                            orderDetail.orderId = order.orderId;
                            orderDetail.productId = c.getItem().getFindId();
                            orderDetail.productName = c.getItem().getName();
                            orderDetail.productImage = c.getItem().getImage();
                            orderDetail.productPrice = c.getItem().getPrice();
                            orderDetail.productCount = c.getCount();
                            orderDetail.totalPrice = c.getCount() * c.getItem().getPrice();
                            orderDetail.discount = 10;
                            orderDetail.productType = c.getItem().getType();
                            orderDetails.add(orderDetail);
                        }
                        db.orderDetailDao().insertOrderDetails(orderDetails);

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        ((Activity) getContext()).finish();

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showMessageMissPayment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Please add your payment information.");

        builder.setPositiveButton("Enter payment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                isHaveAddress = true;
                Intent intent = new Intent(getContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}