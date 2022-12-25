package com.kunle.shoppinglistapp.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


//this is a table for the Grocery List. This table is temporary, while the Food model is permanent for future list building
@Entity(tableName = "temp_grocery_list")
public class GroceryList {

    @PrimaryKey
    private long groceryId;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "quantity")
    private String quantity;

    @Ignore
    public GroceryList() {
    }

    @Ignore
    public GroceryList(@NonNull String name) {
        this();
        this.name = name;
    }

    public GroceryList(@NonNull String name, String quantity) {
        this(name);
        this.quantity = quantity;
    }


    public static GroceryList parseGroceryList(Food food) {
        GroceryList converted = new GroceryList();

        if (food.getName() != null) {
            converted.setName(food.getName());
        }
        if (food.getQuantity() != null) {
            converted.setQuantity(food.getQuantity());
        }

        return converted;
    }

    public long getGroceryId() {
        return groceryId;
    }

    public void setGroceryId(long groceryId) {
        this.groceryId = groceryId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
