package com.project.stacklab.Activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.project.stacklab.Database.AppDatabase;
import com.project.stacklab.Models.OrderDetailModel;
import com.project.stacklab.Models.OrderModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.ActivityRevenueBinding;

import java.util.ArrayList;
import java.util.List;

public class RevenueActivity extends AppCompatActivity {

    ActivityRevenueBinding binding;

    List<OrderModel> orders;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRevenueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getInstance(this);

        orders = db.orderDao().getOrders();

        int success = 0;
        int pending = 0;
        int cancelled = 0;
        double totalRevenue = 0;
        int soldItem = 0;
        double[] mothlyRevenue = new double[12];

        for (OrderModel order : orders) {
            if (order.status.equals("Success")) {
                success++;
                String month = order.date.split("-")[1];
                mothlyRevenue[Integer.parseInt(month) - 1] += order.totalPrice;
                totalRevenue += order.totalPrice;
                List<OrderDetailModel> items = db.orderDetailDao().getOrderDetaills(order.orderId);
                soldItem += items.stream().reduce(0, (subtotal, item) -> subtotal + item.productCount, Integer::sum);
            } else if (order.status.equals("Pending")) {
                pending++;
            } else if (order.status.equals("Cancelled")) {
                cancelled++;
            }
        }

        binding.tvTotalRevenue.setText(String.valueOf(totalRevenue));
        binding.tvTotalOrder.setText(String.valueOf(orders.size()));
        binding.tvSoldItems.setText(String.valueOf(soldItem));
        binding.tvSuccessfulOrder.setText(String.valueOf(success));

        binding.back.setOnClickListener(view -> {
            finish();
        });

        ArrayList<PieEntry> ordersData = new ArrayList<>();
        if (success > 0) {
            ordersData.add(new PieEntry(success, "Success Order"));
        }
        if (pending > 0) {
            ordersData.add(new PieEntry(pending, "Pending Order"));
        }
        if (cancelled > 0) {
            ordersData.add(new PieEntry(cancelled, "Cancelled Order"));
        }

        PieDataSet orderDataSet = new PieDataSet(ordersData, "Orders");
        orderDataSet.setColors(new int[]{R.color.green, R.color.yellow, R.color.red}, this);
        orderDataSet.setValueTextSize(16f);
        orderDataSet.setValueTextColor(R.color.black);
        PieData dataPieChart = new PieData(orderDataSet);

        binding.pieChart.setData(dataPieChart);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.animate();

        ArrayList<BarEntry> revenueData = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            revenueData.add(new BarEntry(i, (float) mothlyRevenue[i - 1]));
        }

        BarDataSet revenueDataSet = new BarDataSet(revenueData, "Revenue");
        revenueDataSet.setColors(new int[]{R.color.green, R.color.yellow, R.color.red, R.color.blue, R.color.purple, R.color.orange, R.color.grey,
                R.color.green, R.color.yellow, R.color.red, R.color.blue, R.color.purple, R.color.orange, R.color.grey
        }, this);
        revenueDataSet.setValueTextSize(16f);
        revenueDataSet.setValueTextColor(R.color.black);
        revenueDataSet.setDrawValues(false);

        binding.barChart.setData(new BarData(revenueDataSet));
        binding.barChart.animate();

    }
}