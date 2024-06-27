package com.project.stacklab.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.stacklab.Activities.ProductDetail;
import com.project.stacklab.Adapters.SearchAdapter;
import com.project.stacklab.Adapters.WishlistAdapter;
import com.project.stacklab.Database.AppDatabase;
import com.project.stacklab.Models.CartModel;
import com.project.stacklab.Models.ItemModel;
import com.project.stacklab.Models.WishlistModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.FragmentFavouriteBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    FragmentFavouriteBinding binding;

    AppDatabase db;

    List<String> wishlists;

    SearchAdapter searchAdapter;

    ObservableArrayList<CartModel> cartItems;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (getArguments() != null) {
            cartItems = (ObservableArrayList<CartModel>) getArguments().getSerializable("cartItems");
        }
        // Inflate the layout for this fragment
        db = AppDatabase.getInstance(getContext());
        wishlists = new ArrayList<>();
        binding = FragmentFavouriteBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<WishlistModel> wishListDB = db.wishlistDao().getWishList();
        for (WishlistModel wishlist : wishListDB) {
            wishlists.add(wishlist.getFindId());
        }

        List<ItemModel> items = db.itemDao().getAllItems();

        searchAdapter = new SearchAdapter(getContext(), items, wishlists, new SearchAdapter.ItemSelectListener() {
            @Override
            public void onItemSelected(ItemModel item) {
                if (cartItems != null) {
                    cartItems.add(new CartModel(item, 1));
                    Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onImageSelected(ItemModel item) {
                // navigate to product detail
                Intent intent = new Intent(getContext(), ProductDetail.class);
                intent.putExtra("item", item.findId);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onWishlistSelected(ItemModel item) {
                String findId = item.getFindId();

                if (wishlists.contains(findId)) {
                    // remove from wishlist
                    db.wishlistDao().deleteWishlist(findId);
                    Toast.makeText(getContext(), "Removed from wishlist", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // add to wishlist
                    db.wishlistDao().insertWishlist(new WishlistModel(findId));
                    Toast.makeText(getContext(), "Added to wishlist", Toast.LENGTH_SHORT).show();
                }
                searchAdapter.notifyDataSetChanged();
            }
        }, false);

        binding.rvItems.setAdapter(searchAdapter);
        binding.rvItems.setLayoutManager(new GridLayoutManager(getContext(), 1));

    }
}