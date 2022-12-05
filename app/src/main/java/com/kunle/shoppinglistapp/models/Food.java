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
    private int quantity;

    @ColumnInfo(name = "measurement")
    private String measurement;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "temp_group_num")
    private int temp_group_num;


    @Ignore
    public Food() {
    }

    @Ignore
    public Food(String name) {
        this();
        this.name = name;
    }

    //this is for whole Meals that are being included in the main grocery list
    @Ignore
    public Food(String name, int quantity) {
        this(name,quantity,"","Meals included above");
    }

    public Food(String name, int quantity, String measurement, String category) {
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

    public int getTemp_group_num() {
        return temp_group_num;
    }

    public void setTemp_group_num(int temp_group_num) {
        this.temp_group_num = temp_group_num;
    }
}
