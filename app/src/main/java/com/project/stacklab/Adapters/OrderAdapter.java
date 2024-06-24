package com.project.stacklab.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.stacklab.Models.ItemModel;
import com.project.stacklab.Models.OrderModel;
import com.project.stacklab.R;
import com.project.stacklab.databinding.CardItemBinding;
import com.project.stacklab.databinding.CardOrderItemBinding;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    List<OrderModel> orders;

    OrderSelectListener itemSelectListener;
    int selectedPosition;


    public OrderAdapter(Context context, List<OrderModel> orders, OrderSelectListener itemSelectListener) {
        this.context = context;
        this.orders = orders;
        this.itemSelectListener = itemSelectListener;
        selectedPosition = -1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_order_item,parent,false));

    }

    public void setItemList(List<OrderModel> orders) {
        this.orders = orders;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderModel order = orders.get(position);

        holder.binding.date.setText("Date: " + order.date);
        holder.binding.numberProduct.setText("Number of Products: " + order.numberProducts.toString());
        holder.binding.total.setText("Total: $" + (order.totalPrice - order.discount));
        holder.binding.status.setText("Status: " + order.status);
        holder.binding.cardOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSelectListener.onOrderSelected(order);
            }
        });



    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        CardOrderItemBinding binding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CardOrderItemBinding.bind(itemView);
        }
    }

    public interface OrderSelectListener {

        void onOrderSelected(OrderModel order);

    }
}
