package com.project.stacklab.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.stacklab.Activities.ProductDetail;
import com.project.stacklab.Adapters.BannerPagerAdapter;
import com.project.stacklab.Adapters.CategoriesAdapter;
import com.project.stacklab.Adapters.ItemAdapter;
import com.project.stacklab.Database.AppDatabase;
import com.project.stacklab.Models.CartModel;
import com.project.stacklab.Models.CategoryModel;
import com.project.stacklab.Models.ItemModel;
import com.project.stacklab.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    BannerPagerAdapter bannerAdapter;

    CategoriesAdapter categoriesAdapter;

    List<CategoryModel> categories;
    List<ItemModel> items;

    ObservableArrayList<CartModel> cartItems;

    ItemAdapter itemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAutoScrollBanner();

        if (getArguments() != null) {
            cartItems = (ObservableArrayList<CartModel>) getArguments().getSerializable("cartItems");
        }
        setCatLog();

    }

    private void setCatLog() {
        AppDatabase db = AppDatabase.getInstance(getContext());
        categories = db.categoryDao().getAllCategories();

        categoriesAdapter = new CategoriesAdapter(getContext(), categories,
                new CategoriesAdapter.CategorySelectListener() {
                    @Override
                    public void onCategorySelected(CategoryModel category) {
                        items.clear();
                        items.addAll(db.itemDao().getItemsForCategory(category.getName()));
                        // Toast.makeText(getContext(), String.valueOf(items.size()),
                        // Toast.LENGTH_SHORT).show();
                        itemAdapter.notifyDataSetChanged();
                        categoriesAdapter.notifyDataSetChanged();
                    }
                });

        binding.rvCategory.setAdapter(categoriesAdapter);
        binding.rvCategory
                .setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvCategory.setHasFixedSize(true);

        items = new ArrayList<>();
        itemAdapter = new ItemAdapter(getContext(), items, new ItemAdapter.ItemSelectListener() {
            @Override
            public void onItemSelected(ItemModel Item) {
                if (cartItems != null) {
                    cartItems.add(new CartModel(Item, 1));
                    Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onImageSelected(ItemModel item) {
                Intent intent = new Intent(getContext(), ProductDetail.class);
                intent.putExtra("item", item.findId);
                startActivityForResult(intent, 1);
            }
        });

        binding.rvItems.setAdapter(itemAdapter);
        binding.rvItems.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rvItems.setHasFixedSize(true);

        categoriesAdapter.getCategorySelectListener().onCategorySelected(categories.get(0));
        categoriesAdapter.setSelectedPosition(0);

        binding.btnSearch.setOnClickListener(view1 -> {
            String search = binding.etSearch.getText().toString();
            List<ItemModel> searchItems = items.stream().filter(item -> item.getName().toLowerCase().contains(search.toLowerCase())).collect(Collectors.toList());
            itemAdapter.setItemList(searchItems);
            itemAdapter.notifyDataSetChanged();
        });


    }

    private void setAutoScrollBanner() {
        bannerAdapter = new BannerPagerAdapter(getContext());
        binding.bannerKocMarket.setAdapter(bannerAdapter);
        binding.tabDots.setupWithViewPager(binding.bannerKocMarket, true);

        binding.bannerKocMarket.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // Handle page selection
            }
        });

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = binding.bannerKocMarket.getCurrentItem();
                int nextItem = (currentItem + 1) % bannerAdapter.getCount();
                binding.bannerKocMarket.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(runnable, 5000);
    }

}