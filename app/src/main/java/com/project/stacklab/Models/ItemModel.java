package com.project.stacklab.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "items")
public class ItemModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "findId")
    public String findId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "price")
    public Integer price;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "description")
    public String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFindId() {
        return this.name + this.price + this.type + this.category;
    }

}