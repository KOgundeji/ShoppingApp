package com.kunle.shoppinglistapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.util.Converters;

import java.util.List;


@Dao
@TypeConverters({Converters.class})
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);

    @Query("DELETE FROM meal_table")
    void deleteAllMeals();

    @Update
    void updateMeal(Meal meal);

    @Query("SELECT * FROM meal_table")
    LiveData<List<Meal>> getAllMeals();



}
