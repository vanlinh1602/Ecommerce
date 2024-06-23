package com.project.stacklab.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.project.stacklab.Models.CategoryModel;
import com.project.stacklab.Models.ItemModel;
import com.project.stacklab.Models.WishlistModel;

@Database(entities = {CategoryModel.class, ItemModel.class, WishlistModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract ItemDao itemDao();
    public  abstract WishlistDao wishlistDao();
    private static AppDatabase instance;
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "product_db").allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}