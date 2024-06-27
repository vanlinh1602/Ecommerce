package com.project.stacklab.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.stacklab.Models.OrderModel;

import java.util.List;
@Dao
public interface OrderDao {
    @Insert
    void insertOrder(OrderModel item);

    @Query("SELECT * FROM orders")
    List<OrderModel> getOrders();

    @Query("SELECT * FROM orders where orderId = :orderId")
    OrderModel getOrder(String orderId);

    @Query("UPDATE orders SET status = :status where id = :id")
    void updateOrderStatus(int id, String status);

}