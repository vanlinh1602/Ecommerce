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
import com.project.stacklab.R;
import com.project.stacklab.databinding.ActivityRevenueBinding;

import java.util.ArrayList;

public class RevenueActivity extends AppCompatActivity {

    ActivityRevenueBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRevenueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> {
            finish();
        });

        ArrayList<PieEntry> ordersData = new ArrayList<>();
        ordersData.add(new PieEntry(18.5f, "Success Order"));
        ordersData.add(new PieEntry(26.7f, "Pending Order"));
        ordersData.add(new PieEntry(24.0f, "Cancelled Order"));

        PieDataSet orderDataSet = new PieDataSet(ordersData, "Orders");
        orderDataSet.setColors(new int[]{R.color.green, R.color.yellow, R.color.red}, this);
        orderDataSet.setValueTextSize(16f);
        orderDataSet.setValueTextColor(R.color.black);
        PieData dataPieChart = new PieData(orderDataSet);

        binding.pieChart.setData(dataPieChart);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.animate();



        ArrayList<BarEntry> revenueData = new ArrayList<>();
        revenueData.add(new BarEntry(1, 18.5f));
        revenueData.add(new BarEntry(2, 26.7f));
        revenueData.add(new BarEntry(3, 24.0f));
        revenueData.add(new BarEntry(4, 20.0f));
        revenueData.add(new BarEntry(5, 30.0f));
        revenueData.add(new BarEntry(6, 25.0f));

        BarDataSet revenueDataSet = new BarDataSet(revenueData, "Revenue");
        revenueDataSet.setColors(new int[]{R.color.green, R.color.yellow, R.color.red, R.color.blue, R.color.purple, R.color.orange}, this);
        revenueDataSet.setValueTextSize(16f);
        revenueDataSet.setValueTextColor(R.color.black);
        revenueDataSet.setDrawValues(false);

        binding.barChart.setData(new BarData(revenueDataSet));
        binding.barChart.animate();

    }
}