package com.kunle.shoppinglistapp.util;

import android.content.Context;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kunle.shoppinglistapp.data.FoodDao;
import com.kunle.shoppinglistapp.data.MealDao;
import com.kunle.shoppinglistapp.data.MealWithIngredientsDao;
import com.kunle.shoppinglistapp.data.SettingsDao;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.models.MealFoodMap;
import com.kunle.shoppinglistapp.models.Settings;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//This is creating the actual RoomDatabase, which is comprised of the Entities, DAO, and SQLite to form our main database

@Database(entities = {Food.class, Meal.class, MealFoodMap.class, Settings.class},
        version = 1, exportSchema = false)
public abstract class ShoppingRoomDB extends RoomDatabase {

    public abstract FoodDao foodDao();
    public abstract MealDao mealDao();
    public abstract MealWithIngredientsDao mealWithIngredientsDao();
    public abstract SettingsDao settingsDao();

    public static final int NUMBER_OF_THREADS = 4;

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
                            .addCallback(sRoomDatabaseCallback)
//                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
//                            .addMigrations(MIGRATION)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE food_table RENAME name TO foodName");
        }
    };

//    this is here is to create something when the database first runs
    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            SettingsDao settingsDao = INSTANCE.settingsDao();
                            settingsDao.deleteAllSettings(); //to start fresh

                            settingsDao.insertSettings(new Settings(Settings.SCREEN_ON,0));
                            settingsDao.insertSettings(new Settings(Settings.NO_CATEGORIES,0));
                        }
                    });
                }

            };
}
