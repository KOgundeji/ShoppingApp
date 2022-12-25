package com.kunle.shoppinglistapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.kunle.shoppinglistapp.models.Meal;
import java.util.List;


@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);

    @Update
    void updateMeal(Meal meal);

    @Query("DELETE FROM MEAL_TABLE")
    void deleteAllMeals();

    @Query("SELECT * FROM meal_table WHERE name = :name")
    Meal getMeal(String name);

    @Query("SELECT * FROM MEAL_TABLE")
    LiveData<List<Meal>> getAllMeals();
}
