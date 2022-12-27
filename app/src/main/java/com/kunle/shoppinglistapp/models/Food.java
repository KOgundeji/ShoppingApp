package com.kunle.shoppinglistapp.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_table")
public class Food {

    @PrimaryKey(autoGenerate = true)
    private long foodId;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "quantity")
    private String quantity;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "grocery_list")
    private boolean isInGroceryList;

    @Ignore
    public Food() {
    }

    //this is for meals that are included in grocery list
    @Ignore
    public Food(String name, String quantity) {
        this(name,quantity,"Meals",true);
    }

    @Ignore
    public Food(String name, String quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public Food(String name, String quantity, String category, boolean isInGroceryList) {
        this(name, quantity, category);
        this.isInGroceryList = isInGroceryList;
    }

    public long getFoodId() {
        return foodId;
    }

    public void setFoodId(long foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isInGroceryList() {
        return isInGroceryList;
    }

    public void setInGroceryList(boolean inGroceryList) {
        isInGroceryList = inGroceryList;
    }
}
