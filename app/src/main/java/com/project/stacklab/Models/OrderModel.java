package com.project.stacklab.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class OrderModel {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "orderId")
    public String orderId;
    @ColumnInfo(name = "numberProducts")
    public Integer numberProducts;
    @ColumnInfo(name = "totalPrice")
    public Integer totalPrice;
    @ColumnInfo(name = "discount")
    public Integer discount;
    @ColumnInfo(name = "shipping")
    public Integer shipping;
    @ColumnInfo(name = "status")
    public String status;
    @ColumnInfo(name = "date")
    public String date;

    public OrderModel() {
    }

    public OrderModel(String orderId, Integer numberProducts, Integer totalPrice, Integer discount, Integer shipping, String status, String date) {
        this.orderId = orderId;
        this.numberProducts = numberProducts;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.shipping = shipping;
        this.status = status;
        this.date = date;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
