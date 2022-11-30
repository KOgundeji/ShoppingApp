package com.kunle.shoppinglistapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MealWithIngredientsDao {

    @Transaction
    @Query("SELECT * FROM meal_table")
    List<MealWithIngredients> getAllMeals();

}
