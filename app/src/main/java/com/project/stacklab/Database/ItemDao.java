package com.project.stacklab.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.stacklab.Models.ItemModel;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    long insertItem(ItemModel item);

    @Query("SELECT * FROM items WHERE category = :categoryName")
    List<ItemModel> getItemsForCategory(String categoryName);

    @Query("SELECT * FROM items WHERE findId = :itemId")
    ItemModel getItemById(String itemId);

    @Query("SELECT * FROM items WHERE findId in (:itemIds)")
    List<ItemModel> getItemsByFindIds(List<String> itemIds);

    @Query("SELECT * FROM items")
    List<ItemModel> getAllItems();
}
