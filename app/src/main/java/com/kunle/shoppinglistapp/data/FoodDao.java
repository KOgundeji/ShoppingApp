package com.kunle.shoppinglistapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.util.Converters;

import java.util.List;

@Dao
@TypeConverters({Converters.class})
public interface FoodDao {
    //takes care of CRUD operations (Create, Read, Update, Delete)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFood(Food food);

    @Query("DELETE FROM food_table")
    void deleteAllFood();

    @Delete
    void deleteFood(Food food);

    @Query("SELECT * FROM food_table")
    List<Meal> getAllFood();

}
