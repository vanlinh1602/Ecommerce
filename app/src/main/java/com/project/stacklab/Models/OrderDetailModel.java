package com.project.stacklab.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ordersDetail")
public class OrderDetailModel {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(name = "orderId")
    public String orderId;
    @ColumnInfo(name = "productId")
    public String productId;
    @ColumnInfo(name = "productName")
    public String productName;
    @ColumnInfo(name = "productImage")
    public String productImage;
    @ColumnInfo(name = "productPrice")
    public Integer productPrice;
    @ColumnInfo(name = "productCount")
    public Integer productCount;
    @ColumnInfo(name = "productType")
    public String productType;
    @ColumnInfo(name = "totalPrice")
    public Integer totalPrice;
    @ColumnInfo(name = "discount")
    public Integer discount;
}
