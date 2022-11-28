package com.kunle.shoppinglistapp.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.util.ShoppingRoomDatabase;

import java.util.List;

//this is a helper class. It creates a central repository to fetch DB data. All the data you need is here.
//Its not absolutely necessary, you could fetch the data within the Activity, but organizationally,this is easier.

public class ShoppingRepository {
    private MealDao mealDao;
    private FoodDao foodDao;
    private LiveData<List<Meal>> allMeals;

    public ShoppingRepository(Application application) {
        ShoppingRoomDatabase db = ShoppingRoomDatabase.getDatabase(application);
        mealDao = db.mealDao();
        foodDao = db.foodDao();

        allMeals = mealDao.getAllMeals();
    }

    public LiveData<List<Meal>> getAllData() {
        return allMeals;
    }

    public void insert(Meal meal) {
        ShoppingRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mealDao.insert(meal);
            }
        });
    }
}
