package com.project.stacklab.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wishlist")
public class WishlistModel {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "findId")
    public String findId;

    public WishlistModel() {
    }

    public WishlistModel(String findId) {
        this.findId = findId;
    }

    public String getFindId() {
        return this.findId;
    }

    public void setFindId(String findId) {
        this.findId = findId;
    }

}