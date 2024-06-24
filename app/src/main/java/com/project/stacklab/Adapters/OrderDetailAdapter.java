package com.project.stacklab.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.stacklab.Models.CartModel;
import com.project.stacklab.Models.OrderDetailModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.CardCartItemBinding;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {

    Context context;
    List<OrderDetailModel> orderDetails;
    int selectedPosition;

    public OrderDetailAdapter(Context context, List<OrderDetailModel> orderDetails) {
        this.context = context;
        this.orderDetails = orderDetails;
        selectedPosition = -1;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderDetailAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderDetailModel orderDetail = orderDetails.get(position);
        holder.binding.name.setText(orderDetail.productName);
        holder.binding.price.setText("$" + orderDetail.totalPrice.toString());
        holder.binding.type.setText(orderDetail.productType);
        holder.binding.count.setText(orderDetail.productCount.toString());
        Glide.with(context)
                .load(orderDetail.productImage)
                .centerCrop()
                .placeholder(R.drawable.adidas)
                .into(holder.binding.image);

        holder.binding.increase.setVisibility(View.GONE);
        holder.binding.decrease.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardCartItemBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CardCartItemBinding.bind(itemView);
        }
    }
}
