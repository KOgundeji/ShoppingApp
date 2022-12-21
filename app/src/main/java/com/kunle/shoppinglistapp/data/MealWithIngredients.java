package com.kunle.shoppinglistapp.data;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.models.MealFoodMap;

import java.util.List;

public class MealWithIngredients {
    @Embedded
    public Meal meal;
    @Relation(
            parentColumn = "mealId",
            entityColumn = "foodId",
            associateBy = @Junction(MealFoodMap.class)
    )

    public List<Food> foodList;

    public Meal getMeal() {
        return meal;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

}
