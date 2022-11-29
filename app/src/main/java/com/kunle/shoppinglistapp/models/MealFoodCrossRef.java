package com.kunle.shoppinglistapp.models;

import androidx.room.Entity;

@Entity(primaryKeys = {"mealId","foodId"})
public class MealFoodCrossRef {
    public long mealId;
    public long foodId;

}
