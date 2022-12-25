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

    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "quantity")
    private String quantity;

    @Ignore
    public Food() {
    }

    @Ignore
    public Food(@NonNull String name) {
        this.name = name;
    }

    public Food(@NonNull String name, String quantity) {
        this(name);
        this.quantity = quantity;
    }



    public static Food parseFood(GroceryList grocery) {
        Food converted = new Food();

        if (grocery.getName() != null) {
            converted.setName(grocery.getName());
        }
        if (grocery.getQuantity() != null) {
            converted.setQuantity(grocery.getQuantity());
        }

        return converted;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public long getFoodId() {
        return foodId;
    }

    public void setFoodId(long foodId) {
        this.foodId = foodId;
    }
}
