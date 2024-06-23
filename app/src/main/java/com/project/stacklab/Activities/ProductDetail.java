package com.project.stacklab.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.ObservableArrayList;

import com.bumptech.glide.Glide;
import com.project.stacklab.Database.AppDatabase;
import com.project.stacklab.Models.CartModel;
import com.project.stacklab.Models.ItemModel;
import com.project.stacklab.Models.WishlistModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.ActivityProductDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductDetail extends AppCompatActivity {

    ActivityProductDetailBinding binding;
    AppDatabase db;

    ItemModel itemModel;

    boolean isWishlisted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String id = getIntent().getStringExtra("item");

        db = AppDatabase.getInstance(ProductDetail.this);

        List<WishlistModel> wishlistModels = db.wishlistDao().getWishList();
        for (WishlistModel wishlistModel : wishlistModels) {
            if (wishlistModel.findId.equals(id)) {
                isWishlisted = true;
                break;
            }
        }

        if (isWishlisted) {
            binding.wishlist.setForeground(ContextCompat.getDrawable(this, R.drawable.ic_heart_full));
        } else {
            binding.wishlist.setForeground(ContextCompat.getDrawable(this, R.drawable.ic_heart));
        }

        itemModel = db.itemDao().getItemById(id);
        binding.prdName.setText(itemModel.getName());
        binding.title.setText(itemModel.getName());
        binding.price.setText("$ " + itemModel.getPrice());
        binding.description.setText(itemModel.description);

        Glide.with(this)
                .load(itemModel.getImage())
                .centerCrop()
                .placeholder(R.drawable.adidas)
                .into(binding.image);

        binding.back.setOnClickListener(view -> {
            finish();
        });

        binding.wishlist.setOnClickListener(view -> {
           wishListHandle(id);
        });

        binding.addToCart.setOnClickListener(view -> {
            addToCartHandle(itemModel);
        });
    }

    protected  void addToCartHandle(ItemModel itemModel) {
        // CartModel cartModel = new CartModel(itemModel, 1);
        // cartItems.add(cartModel);
        Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
    }
    protected void wishListHandle(String id) {
        if (isWishlisted) {
            binding.wishlist.setForeground(ContextCompat.getDrawable(this, R.drawable.ic_heart));
            db.wishlistDao().deleteWishlist(id);
            Toast.makeText(this, "Removed from wishlist", Toast.LENGTH_SHORT).show();
            isWishlisted = false;
        } else {
            WishlistModel wishlistModel = new WishlistModel();
            wishlistModel.findId = id;
            db.wishlistDao().insertWishlist(wishlistModel);
            binding.wishlist.setForeground(ContextCompat.getDrawable(this, R.drawable.ic_heart_full));
            Toast.makeText(this, "Added to wishlist", Toast.LENGTH_SHORT).show();
            isWishlisted = true;
        }
    }
}