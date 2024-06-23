package com.project.stacklab.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.project.stacklab.Fragments.CartFragment;
import com.project.stacklab.Fragments.FavouriteFragment;
import com.project.stacklab.Fragments.HomeFragment;
import com.project.stacklab.Fragments.ProfileFragment;
import com.project.stacklab.Fragments.SearchFragment;
import com.project.stacklab.Helpers.CartManager;
import com.project.stacklab.Models.CartModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Fragment fragment;
    ObservableArrayList<CartModel> cartItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cartItems = CartManager.loadCartItems(MainActivity.this);
        cartItems.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                binding.bottomNav.setCount(3, String.valueOf(cartItems.size()));
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                binding.bottomNav.setCount(3, String.valueOf(cartItems.size()));
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                binding.bottomNav.setCount(3, String.valueOf(cartItems.size()));
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                binding.bottomNav.setCount(3, String.valueOf(cartItems.size()));
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                binding.bottomNav.setCount(3, String.valueOf(cartItems.size()));
            }
        });

        binding.bottomNav.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        binding.bottomNav.add(new MeowBottomNavigation.Model(2, R.drawable.ic_search));
        binding.bottomNav.add(new MeowBottomNavigation.Model(3, R.drawable.ic_cart));
        binding.bottomNav.add(new MeowBottomNavigation.Model(4, R.drawable.ic_heart));
        binding.bottomNav.add(new MeowBottomNavigation.Model(5, R.drawable.ic_user));
        Bundle bundle = new Bundle();
        bundle.putSerializable("cartItems", cartItems);

        Fragment f1 = new HomeFragment();
        f1.setArguments(bundle);
        Fragment f2 = new SearchFragment();
        f2.setArguments(bundle);
        Fragment f3 = new CartFragment();
        f3.setArguments(bundle);
        Fragment f4 = new FavouriteFragment();
        f4.setArguments(bundle);
        Fragment f5 = new ProfileFragment();


        binding.bottomNav.setCount(3, String.valueOf(cartItems.size()));


        binding.bottomNav.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {

                    case 1:
                        fragment = f1;
                        break;
                    case 2:
                        fragment = f2;
                        break;
                    case 4:
                        fragment = f4;
                        break;
                    case 5:
                        fragment = f5;
                        break;
                    default:
                        fragment = f3;
                        break;

                }

                loadFragment(fragment);
            }
        });

        binding.bottomNav.show(1, true);

        binding.bottomNav.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(MainActivity.this, String.valueOf(item.getId()), Toast.LENGTH_SHORT).show();


            }
        });


        binding.bottomNav.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        CartManager.saveCartItems(MainActivity.this,cartItems);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fc, fragment, null)
                .commit();
    }
}