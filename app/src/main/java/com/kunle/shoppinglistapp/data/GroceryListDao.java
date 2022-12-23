package com.kunle.shoppinglistapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.kunle.shoppinglistapp.models.GroceryList;

import java.util.List;

@Dao
public interface GroceryListDao {
    //takes care of CRUD operations (Create, Read, Update, Delete)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertGroceryItem(GroceryList listItem);

    @Delete
    void deleteGroceryItem(GroceryList listItem);

    @Update
    void updateGroceryItem(GroceryList listItem);

    @Query("DELETE FROM temp_grocery_list")
    void deleteAllGroceries();

    @Query("SELECT * FROM temp_grocery_list")
    LiveData<List<GroceryList>> getAllGroceries();

}
