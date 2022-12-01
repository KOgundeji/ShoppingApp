package com.kunle.shoppinglistapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface MealWithIngredientsDao {

    @Transaction
    @Query("SELECT * FROM meal_table")
    LiveData<List<MealWithIngredients>> getAllMeals();

}
