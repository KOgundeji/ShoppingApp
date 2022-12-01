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
@TypeConverters({Converters.class})
public interface FoodDao {
    //takes care of CRUD operations (Create, Read, Update, Delete)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFood(Food food);

    @Update
    void updateFood(Food food);

    @Delete
    void deleteFood(Food food);

    @Query("DELETE FROM FOOD_TABLE")
    void deleteAllFood();

    @Query("SELECT * FROM FOOD_TABLE")
    LiveData<List<Food>> getAllFood();

}
