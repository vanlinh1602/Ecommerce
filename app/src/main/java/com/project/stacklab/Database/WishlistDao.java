package com.project.stacklab.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.stacklab.Models.WishlistModel;

import java.util.List;

@Dao
public interface WishlistDao {
    @Insert
    void insertWishlist(WishlistModel item);

    @Query("DELETE FROM wishlist WHERE findId = :itemId")
    void deleteWishlist(String itemId);

    @Query("SELECT * FROM wishlist")
    List<WishlistModel> getWishList();

}
