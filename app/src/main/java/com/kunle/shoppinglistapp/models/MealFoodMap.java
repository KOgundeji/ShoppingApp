package com.kunle.shoppinglistapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(primaryKeys = {"mealId", "foodId"})
public class MealFoodMap {
    @ColumnInfo(index = true)
    public long mealId;
    public long foodId;

    @Ignore
    public MealFoodMap() {
    }

    public MealFoodMap(long mealId, long foodId) {
        this.mealId = mealId;
        this.foodId = foodId;
    }

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }

    public long getFoodId() {
        return foodId;
    }

    public void setFoodId(long foodId) {
        this.foodId = foodId;
    }
}
