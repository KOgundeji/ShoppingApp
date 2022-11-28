package com.kunle.shoppinglistapp.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "food_table")
public class Food {

    @PrimaryKey(autoGenerate = true)
 //automatically created, don't need to declare it
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "measurementList")
    private List<String> measurements;

    @ColumnInfo(name = "quantity")
    private int quantity;

    public Food() {
    }

    public Food(@NonNull String name, List<String> measurements) {
        this.name = name;
        this.measurements = measurements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<String> measurements) {
        this.measurements = measurements;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
