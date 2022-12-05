package com.kunle.shoppinglistapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal_table")
public class Meal {

    @PrimaryKey(autoGenerate = true)
    private long mealId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "temp_group_num")
    private int temp_group_num;

    @Ignore
    public Meal() {
    }

    public Meal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }

    public int getTemp_group_num() {
        return temp_group_num;
    }

    public void setTemp_group_num(int temp_group_num) {
        this.temp_group_num = temp_group_num;
    }
}
