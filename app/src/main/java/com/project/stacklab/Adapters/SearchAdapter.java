package com.project.stacklab.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.stacklab.Models.ItemModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.CardItemBinding;
import com.project.stacklab.databinding.CardSearchItemBinding;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    Context context;
    List<ItemModel> itemList;

    List<String> wishlists;

    Boolean isAdmin;

    ItemSelectListener itemSelectListener;
    int selectedPosition;


    public SearchAdapter(Context context, List<ItemModel> itemList, List<String> wishlists,ItemSelectListener itemSelectListener, Boolean isAdmin) {
        this.context = context;
        this.itemList = itemList;
        this.itemSelectListener = itemSelectListener;
        this.wishlists = wishlists;
        this.isAdmin = isAdmin;
        selectedPosition = -1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_search_item,parent,false));

    }

    public void setItemList(List<ItemModel> itemList) {
        this.itemList = itemList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemModel item = itemList.get(position);

        holder.binding.name.setText(item.getName());
        holder.binding.brand.setText(item.getCategory());
        holder.binding.price.setText("$"+item.getPrice().toString());
        Glide.with(context)
                .load(item.getImage())
                .centerCrop()
                .placeholder(R.drawable.adidas)
                .into(holder.binding.image);

        boolean isWishlisted = false;
        if (wishlists.contains(item.getFindId())) {
            isWishlisted = true;
        }

        if (isWishlisted) {
            holder.binding.wishlist.setBackground(context.getDrawable(R.drawable.ic_heart_full));
        } else {
            holder.binding.wishlist.setBackground(context.getDrawable(R.drawable.ic_heart));
        }

        holder.binding.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = holder.getAdapterPosition();
                itemSelectListener.onItemSelected(item);
            }
        });

        // native to product detail when clicked on image
        holder.binding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSelectListener.onImageSelected(item);
            }
        });

        holder.binding.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSelectListener.onWishlistSelected(item);
                if (!wishlists.contains(item.getFindId())) {
                    holder.binding.wishlist.setBackground(context.getDrawable(R.drawable.ic_heart_full));
                    wishlists.add(item.getFindId());
                } else {
                    holder.binding.wishlist.setBackground(context.getDrawable(R.drawable.ic_heart));
                    wishlists.remove(item.getFindId());
                }
            }
        });

        if (isAdmin) {
            holder.binding.wishlist.setVisibility(View.GONE);
            holder.binding.cart.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CardSearchItemBinding binding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CardSearchItemBinding.bind(itemView);
        }
    }


    public interface ItemSelectListener {

        void onItemSelected(ItemModel Item);

        void onImageSelected(ItemModel Item);

        void onWishlistSelected(ItemModel Item);

    }
}
