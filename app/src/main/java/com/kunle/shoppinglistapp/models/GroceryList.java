package com.kunle.shoppinglistapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


//this is a table for the Grocery List. This table is temporary, while the Food model is permanent for future list building
@Entity(tableName = "temp_grocery_list")
public class GroceryList {

    //automatically created, don't need to declare it
    @PrimaryKey(autoGenerate = true)
    private long foodId;

    @ColumnInfo(name = "quantity")
    private String quantity;
    @ColumnInfo(name = "name")
    private String name;

    private String category;


    @Ignore
    public GroceryList() {
    }

    @Ignore
    public GroceryList(String name) {
        this();
        this.name = name;
    }

    //this is for whole Meals that are being included in the main grocery list
    public GroceryList(String name, String quantity) {
        this(name);
        this.quantity = quantity;
    }

    public static GroceryList parseGroceryList(Food food){
        GroceryList converted = new GroceryList();

        if (food.getName() != null) {
            converted.setName(food.getName());
        }
        if (food.getQuantity() != null) {
            converted.setQuantity(food.getQuantity());
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
