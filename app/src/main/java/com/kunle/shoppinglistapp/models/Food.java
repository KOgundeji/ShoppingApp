package com.kunle.shoppinglistapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_table")
public class Food {

    //automatically created, don't need to declare it
    @PrimaryKey(autoGenerate = true)
    private long foodId;

    @ColumnInfo(name = "quantity")
    private String quantity;
    @ColumnInfo(name = "name")
    private String name;


    @Ignore
    public Food() {
    }

    @Ignore
    public Food(String name) {
        this();
        this.name = name;
    }

    public Food(String name, String quantity) {
        this(name);
        this.quantity = quantity;
    }



    public static Food parseFood(GroceryList grocery){
        Food converted = new Food();

        if (grocery.getName() != null) {
            converted.setName(grocery.getName());
        }
        if (grocery.getQuantity() != null) {
            converted.setQuantity(grocery.getQuantity());
        }

        return converted;
    }

    public long getFoodId() {
        return foodId;
    }

    public void setFoodId(long foodId) {
        this.foodId = foodId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
