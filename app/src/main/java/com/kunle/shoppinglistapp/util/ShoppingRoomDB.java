package com.kunle.shoppinglistapp.util;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.kunle.shoppinglistapp.data.FoodDao;
import com.kunle.shoppinglistapp.data.GroceryListDao;
import com.kunle.shoppinglistapp.data.MealDao;
import com.kunle.shoppinglistapp.data.MealWithIngredientsDao;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.GroceryList;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.models.MealFoodCrossRef;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//This is creating the actual RoomDatabase, which is comprised of the Entities, DAO, and SQLite to form our main database

@Database(entities = {Food.class, Meal.class, MealFoodCrossRef.class, GroceryList.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ShoppingRoomDB extends RoomDatabase {

    public abstract FoodDao foodDao();
    public abstract MealDao mealDao();
    public abstract MealWithIngredientsDao mealWithIngredientsDao();
    public abstract GroceryListDao groceryListDao();

    public static final int NUMBER_OF_THREADS = 4; //not sure why we chose this #

    private static volatile ShoppingRoomDB INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //the executor is going to write things into the database, but not through the main thread
    //this will create a new thread for us to use

    public static ShoppingRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShoppingRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ShoppingRoomDB.class, "shopping_database")
//                            .addCallback(sRoomDatabaseCallback) //may just delete this part
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    //I think the only reason this is here is to create something when the database first runs
//    private static final RoomDatabase.Callback sRoomDatabaseCallback =
//            new RoomDatabase.Callback() {
//                @Override
//                public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                    super.onCreate(db);
//                    databaseWriteExecutor.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            FoodDao foodDao = INSTANCE.foodDao();
//                            foodDao.deleteAll(); //to start fresh
//
//                            Food food = new Food();
//                            foodDao.insert(food);
//                        }
//                    });
//                }
//
//            };
}
