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
    private int quantity;

    @ColumnInfo(name = "measurement")
    private String measurement;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "name")
    private String name;


    @Ignore
    public GroceryList() {
    }

    @Ignore
    public GroceryList(String name) {
        this();
        this.name = name;
    }

    //this is for whole Meals that are being included in the main grocery list
    @Ignore
    public GroceryList(String name, int quantity) {
        this(name,quantity,"","Meals included above");
    }

    public GroceryList(String name, int quantity, String measurement, String category) {
        this(name);
        this.quantity = quantity;
        this.measurement = measurement;
        this.category = category;
    }


    public long getFoodId() {
        return foodId;
    }

    public void setFoodId(long foodId) {
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
