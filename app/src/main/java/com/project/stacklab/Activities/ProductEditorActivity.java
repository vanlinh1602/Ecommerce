package com.project.stacklab.Activities;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project.stacklab.Database.AppDatabase;
import com.project.stacklab.Models.ItemModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.ActivityProductEditorBinding;

public class ProductEditorActivity extends AppCompatActivity {

    AppDatabase db;

    ActivityProductEditorBinding binding;

    ItemModel item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getInstance(this);

        String[] categories = {"Nike", "Puma", "Rebook", "Adidas"};
        String[] types = {"Sneaker", "Sport", "Casual", "Formal"};

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);

        binding.spinnerCategory.setAdapter(categoriesAdapter);
        binding.spinnerType.setAdapter(typesAdapter);

        binding.back.setOnClickListener(view -> {
            finish();
        });

        String id = getIntent().getStringExtra("id");
        item = db.itemDao().getItemById(id);
        if (item != null) {
            binding.etProductName.setText(item.getName());
            binding.etProductPrice.setText(String.valueOf(item.getPrice()));
            binding.etProductDescription.setText(item.getDescription());
            binding.etProductImage.setText(item.getImage());
            binding.spinnerCategory.setSelection(categoriesAdapter.getPosition(item.getCategory()));
            binding.spinnerType.setSelection(typesAdapter.getPosition(item.getType()));
        } else {
            item = new ItemModel();
            binding.title.setText("Add Product");
        }

        binding.btnSave.setOnClickListener(view -> {
            item.setName(binding.etProductName.getText().toString());
            item.setPrice((int) Double.parseDouble(binding.etProductPrice.getText().toString()));
            item.setDescription(binding.etProductDescription.getText().toString());
            item.setImage(binding.etProductImage.getText().toString());
            item.setCategory(binding.spinnerCategory.getSelectedItem().toString());
            item.setType(binding.spinnerType.getSelectedItem().toString());

            if (id != null) {
                db.itemDao().updateItem(item.getFindId(), item.getName(), item.getImage(), item.getType(), item.getPrice(), item.getCategory(), item.getDescription());
            } else {
                db.itemDao().insertItem(item);
            }

            setResult(RESULT_OK); // Set the result before finishing the activity
            finish();
        });

    }
}