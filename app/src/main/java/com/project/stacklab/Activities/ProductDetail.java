package com.project.stacklab.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

    boolean isAddedToCart = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        db = AppDatabase.getInstance(ProductDetail.this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("item");

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
            if (isAddedToCart) {
                Intent intent1 = new Intent();;
                intent1.putExtra("item", itemModel);
                setResult(RESULT_OK, intent1);
            }
            finish();
        });

        binding.wishlist.setOnClickListener(view -> {
           wishListHandle(id);
        });

        binding.addToCart.setOnClickListener(view -> {
            addToCartHandle(itemModel);
        });
        setUpSizeAndColor();
     }

    protected  void addToCartHandle(ItemModel itemModel) {
        isAddedToCart = true;
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

    protected  void setUpSizeAndColor() {
        binding.colorOne.setOnClickListener(view -> {
            binding.colorOne.setText("x");
            binding.colorTwo.setText("");
            binding.colorThree.setText("");
            binding.colorFour.setText("");
        });

        binding.colorTwo.setOnClickListener(view -> {
            binding.colorOne.setText("");
            binding.colorTwo.setText("x");
            binding.colorThree.setText("");
            binding.colorFour.setText("");
        });

        binding.colorThree.setOnClickListener(view -> {
            binding.colorOne.setText("");
            binding.colorTwo.setText("");
            binding.colorThree.setText("x");
            binding.colorFour.setText("");
        });

        binding.colorFour.setOnClickListener(view -> {
            binding.colorOne.setText("");
            binding.colorTwo.setText("");
            binding.colorThree.setText("");
            binding.colorFour.setText("x");
        });

        binding.sizeOne.setOnClickListener(view -> {
            binding.sizeOne.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.orange));
            binding.sizeTwo.setBackgroundTintList(null);
            binding.sizeThree.setBackgroundTintList(null);
            binding.sizeFour.setBackgroundTintList(null);
        });

        binding.sizeTwo.setOnClickListener(view -> {
            binding.sizeOne.setBackgroundTintList(null);
            binding.sizeTwo.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.orange));
            binding.sizeThree.setBackgroundTintList(null);
            binding.sizeFour.setBackgroundTintList(null);
        });

        binding.sizeThree.setOnClickListener(view -> {
            binding.sizeOne.setBackgroundTintList(null);
            binding.sizeTwo.setBackgroundTintList(null);
            binding.sizeThree.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.orange));
            binding.sizeFour.setBackgroundTintList(null);
        });

        binding.sizeFour.setOnClickListener(view -> {
            binding.sizeOne.setBackgroundTintList(null);
            binding.sizeTwo.setBackgroundTintList(null);
            binding.sizeThree.setBackgroundTintList(null);
            binding.sizeFour.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.orange));
        });
    }
}