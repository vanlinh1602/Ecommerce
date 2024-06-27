package com.project.stacklab.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.project.stacklab.Adapters.SearchAdapter;
import com.project.stacklab.Adapters.WishlistAdapter;
import com.project.stacklab.Database.AppDatabase;
import com.project.stacklab.Models.CartModel;
import com.project.stacklab.Models.ItemModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.ActivityProductManagementBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductManagementActivity extends AppCompatActivity {

    ActivityProductManagementBinding binding;

    AppDatabase db;

    List<ItemModel> items;

    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getInstance(this);

        items = db.itemDao().getAllItems();

        adapter = new SearchAdapter(this, items, new ArrayList<>(), new SearchAdapter.ItemSelectListener() {
            @Override
            public void onItemSelected(ItemModel item) {
            }
            @Override
            public void onImageSelected(ItemModel item) {
                Intent intent = new Intent(ProductManagementActivity.this, ProductEditorActivity.class);
                intent.putExtra("id", item.findId);
                startActivityForResult(intent, 1); // 1 is the request code
            }
            @Override
            public void onWishlistSelected(ItemModel item) {
            }
        }, true);

        binding.rvProducts.setAdapter(adapter);
        binding.rvProducts.setLayoutManager(new GridLayoutManager(this, 1));

        binding.back.setOnClickListener(view -> {
            finish();
        });

        binding.addProduct.setOnClickListener(view -> {
            Intent intent = new Intent(ProductManagementActivity.this, ProductEditorActivity.class);
            startActivityForResult(intent, 1); // 1 is the request code
            finish();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the request code is the same as what we passed to startActivityForResult()
        if (requestCode == 1) {
            // If the result code is RESULT_OK, that means the ProductEditorActivity finished successfully
            if (resultCode == RESULT_OK) {
                // Refresh the items list
                items = db.itemDao().getAllItems();
                adapter.setItemList(items);
                adapter.notifyDataSetChanged();
            }
        }
    }
}