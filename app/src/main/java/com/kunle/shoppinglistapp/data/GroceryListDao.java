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
import com.kunle.shoppinglistapp.models.GroceryList;
import com.kunle.shoppinglistapp.util.Converters;

import java.util.List;

@Dao
@TypeConverters({Converters.class})
public interface GroceryListDao {
    //takes care of CRUD operations (Create, Read, Update, Delete)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGroceryItem(GroceryList listItem);

    @Update
    void updateGroceryItem(GroceryList listItem);

    @Delete
    void deleteGroceryItem(GroceryList listItem);

    @Query("DELETE FROM FOOD_TABLE")
    void deleteAllGroceries();

    @Query("SELECT * FROM FOOD_TABLE")
    LiveData<List<GroceryList>> getAllGroceries();

}
