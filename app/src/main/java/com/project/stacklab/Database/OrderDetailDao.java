package com.project.stacklab.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.stacklab.Models.OrderDetailModel;

import java.util.List;

@Dao
public interface OrderDetailDao {
    @Insert
    void insertOrderDetail(OrderDetailModel item);

    @Insert
    void insertOrderDetails(List<OrderDetailModel> items);

    @Query("SELECT * FROM ordersDetail where orderId = :orderId")
    List<OrderDetailModel> getOrderDetaills(String orderId);
}
