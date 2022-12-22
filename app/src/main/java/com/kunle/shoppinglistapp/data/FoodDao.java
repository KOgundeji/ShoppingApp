package com.kunle.shoppinglistapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.util.Converters;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FoodDao {
    //takes care of CRUD operations (Create, Read, Update, Delete)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFood(Food food);

    @Update
    void updateFood(Food food);

    @Delete
    void deleteFood(Food food);

    @Query("DELETE FROM food_table")
    void deleteAllFood();

    @Query("SELECT * FROM food_table WHERE foodId = :foodId")
    Food getFood(Long foodId);

    @Query("SELECT * FROM food_table")
    LiveData<List<Food>> getAllFood();

}
